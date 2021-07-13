package net.dengzixu.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.java.constant.Constant;
import net.dengzixu.java.constant.PacketOperationEnum;
import net.dengzixu.java.constant.PacketProtocolVersionEnum;
import net.dengzixu.java.listener.Listener;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.packet.Packet;
import net.dengzixu.java.packet.PacketBuilder;
import net.dengzixu.java.packet.PacketResolve;
import net.dengzixu.java.payload.AuthPayload;
import net.dengzixu.java.payload.PayloadResolver;
import net.dengzixu.java.third.api.GetAuthToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DanmuResolver {
    private final long roomId;
    private static DanmuResolver danmuResolver = null;

    private List<Listener> listenerList = new ArrayList<>();

    private OkHttpClient okHttpClient;
    private Request request;
    private WebSocket webSocket;

    private Timer heartbeatTimer;

    private DanmuResolver(long roomId) {
        this.roomId = roomId;

        this.initWebsocket();
    }

    public static DanmuResolver getInstance(long roomId) {
        if (null == danmuResolver) {
            synchronized (DanmuResolver.class) {
                if (danmuResolver == null) {
                    danmuResolver = new DanmuResolver(roomId);
                }
            }
        }
        return danmuResolver;
    }

    public void addListener(Listener listener) {
        if (null != listener) {
            listenerList.add(listener);
        }
    }

    private void initWebsocket() {
        okHttpClient = new OkHttpClient.Builder()
                .build();

        request = new Request.Builder()
                .url(Constant.BILIBILI_LIVE_WS_URL)
                .build();

        webSocket = okHttpClient.newWebSocket(request, createWebSocketListener());
    }

    /**
     * 开始发送心跳
     */
    private void startHeartbeat() {
        if (null == heartbeatTimer) {
            heartbeatTimer = new Timer();
        }

        heartbeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] bytes = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
                        PacketOperationEnum.OPERATION_2,
                        "[object Object]").buildArrays();
                webSocket.send(new ByteString(bytes));
            }
        }, 0, 1000 * 30);
    }

    /**
     * 停止发送心跳
     */
    private void stopHeartbeat() {
        if (null != heartbeatTimer) {
            heartbeatTimer.cancel();
        }
    }

    private WebSocketListener createWebSocketListener() {
        return new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                List<Packet> packets = new PacketResolve(bytes.toByteArray()).getPacketList();

                if (packets.size() > 0) {
                    for (Packet packet : packets) {
                        Message message = new PayloadResolver(packet.getPayload(),
                                PacketOperationEnum.getEnum(packet.getOperation())).resolve();

                        switch (message.getBodyCommand()) {
                            case DANMU_MSG:
                            case INTERACT_WORD:
                            case SEND_GIFT:
                                listenerList.forEach((listener -> {
                                    listener.onMessage(message);
                                }));
                                break;
                            case AUTH_SUCCESS:
                                System.out.println("认证成功");
                                startHeartbeat();
                                break;
                            case UNKNOWN:
                                break;
                            default:
                        }
                    }
                }
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                this.onAuth(webSocket, response);
            }

            private void onAuth(@NotNull WebSocket webSocket, @NotNull Response response) {
                AuthPayload authPayload = new AuthPayload();
                authPayload.setRoomid(roomId);
                authPayload.setKey(GetAuthToken.get(roomId));

                String payloadString;

                try {
                    payloadString = new ObjectMapper().writeValueAsString(authPayload);
                } catch (JsonProcessingException ignored) {
                    webSocket.close(1001, "");
                    return;
                }

                if (null != payloadString) {
                    byte[] packetArray = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
                            PacketOperationEnum.OPERATION_7,
                            payloadString).buildArrays();
                    webSocket.send(new ByteString(packetArray));
                }
            }
        };
    }
}
