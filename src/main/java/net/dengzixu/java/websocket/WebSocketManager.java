package net.dengzixu.java.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.java.constant.Constant;
import net.dengzixu.java.constant.PacketOperationEnum;
import net.dengzixu.java.constant.PacketProtocolVersionEnum;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.packet.*;
import net.dengzixu.java.payload.AuthPayload;
import net.dengzixu.java.payload.PayloadResolver;
import net.dengzixu.java.third.api.GetAuthToken;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Deprecated
public class WebSocketManager {
    private static WebSocketManager webSocketManager = null;

    private OkHttpClient okHttpClient;
    private Request request;
    private WebSocket webSocket;

    private Timer heartbeatTimer;

    private boolean connect;

    private final long roomId;

    private WebSocketManager(long roomId) {
        this.roomId = roomId;
    }

    /**
     * getInstance
     *
     * @param roomId 房间ID
     * @return WebSocketManager
     */
    public static WebSocketManager getInstance(long roomId) {
        if (null == webSocketManager) {
            synchronized (WebSocketManager.class) {
                if (null == webSocketManager) {
                    webSocketManager = new WebSocketManager(roomId);
                }
            }
        }
        return webSocketManager;
    }

    /**
     * 初始化
     */
    public void init() {
        okHttpClient = new OkHttpClient.Builder()
                .build();

        request = new Request.Builder()
                .url(Constant.BILIBILI_LIVE_WS_URL)
                .build();
    }

    /**
     * 建立链接
     */
    public void connect() {
        webSocket = okHttpClient.newWebSocket(request, createWebSocketListener());
    }

    public boolean sendMessage(String data) {
        return webSocket.send(data);
    }

    public boolean sendMessage(ByteString data) {
        return webSocket.send(data);
    }

    public void close() {
        webSocket.close(1001, "");
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
                stopHeartbeat();
                connect = false;
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                connect = false;
                System.out.println("Websocket onFailure");
                stopHeartbeat();
                close();
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
                                System.out.println(message);
                                break;
                            case AUTH_SUCCESS:
                                System.out.println("认证成功");
                                connect = true;
                                startHeartbeat();
                                break;
                            case UNKNOWN:
                                break;
                            default:
                        }
                    }
                }

                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("准备建立 Websocket 链接...");

                // 构建认证 payload
                AuthPayload authPayload = new AuthPayload();
                authPayload.setRoomid(roomId);
                authPayload.setKey(GetAuthToken.get(roomId));

                String payloadString;

                try {
                    payloadString = new ObjectMapper().writeValueAsString(authPayload);
                } catch (JsonProcessingException ignored) {
                    close();
                    return;
                }

                if (null != payloadString) {
                    byte[] packetArray = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
                            PacketOperationEnum.OPERATION_7,
                            payloadString).buildArrays();
                    webSocket.send(new ByteString(packetArray));
                }

                super.onOpen(webSocket, response);
            }
        };
    }
}
