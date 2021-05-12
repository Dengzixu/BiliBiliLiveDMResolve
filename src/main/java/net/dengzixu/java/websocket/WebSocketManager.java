package net.dengzixu.java.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.java.constant.Constant;
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

    public void init() {
        okHttpClient = new OkHttpClient.Builder()
                .build();

        request = new Request.Builder()
                .url(Constant.BILIBILI_LIVE_WS_URL)
                .build();
    }

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

    private void startHeartbeat() {
        if (null == heartbeatTimer) {
            heartbeatTimer = new Timer();
        }

        heartbeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] bytes = new PacketBuilder(ProtocolVersion.PROTOCOL_VERSION_1,
                        Operation.OPERATION_2,
                        "[object Object]").buildArrays();
                webSocket.send(new ByteString(bytes));
            }
        }, 0, 1000 * 30);
    }

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
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                connect = false;
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                connect = false;
                System.out.println("Websocket onFailure");
                stopHeartbeat();
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println("onMessage Bytes:" + text);
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                List<Packet> packets = new PacketResolve(bytes.toByteArray()).getPacketList();

                if (packets.size() > 0) {
                    for (Packet packet : packets) {
                        Message message = new PayloadResolver(packet.getPayload(),
                                packet.getOperation()).resolve();

                        switch (message.getBodyCommand()) {
                            case DANMU_MSG:
                            case INTERACT_WORD:
                            case SEND_GIFT:
                                System.out.println(message);
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
                System.out.println("建立链接");

                // 构建认证 payload
                AuthPayload authPayload = new AuthPayload();
                authPayload.setRoomid(roomId);
                authPayload.setKey(GetAuthToken.get(roomId));

                String payloadString = null;

                try {
                    payloadString = new ObjectMapper().writeValueAsString(authPayload);
                } catch (JsonProcessingException ignored) {

                }

                if (null != payloadString) {
                    byte[] packetArray = new PacketBuilder(ProtocolVersion.PROTOCOL_VERSION_1,
                            Operation.OPERATION_7,
                            payloadString).buildArrays();

                    webSocket.send(new ByteString(packetArray));
                }

                connect = true;

                startHeartbeat();
                super.onOpen(webSocket, response);
            }
        };
    }
}
