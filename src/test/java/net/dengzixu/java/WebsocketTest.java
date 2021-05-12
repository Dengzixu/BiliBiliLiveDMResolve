package net.dengzixu.java;

import net.dengzixu.java.third.api.GetAuthToken;
import net.dengzixu.java.websocket.WebSocketManager;

public class WebsocketTest {
    private static final long ROOM_ID = 3080147;

    public static void main(String[] args) {
        WebSocketManager webSocketManager = WebSocketManager.getInstance(ROOM_ID);
        webSocketManager.init();
        webSocketManager.connect();
    }
}
