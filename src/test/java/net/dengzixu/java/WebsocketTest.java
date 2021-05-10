package net.dengzixu.java;

import net.dengzixu.java.third.api.GetAuthToken;
import net.dengzixu.java.websocket.WebSocketManager;

public class WebsocketTest {
    public static void main(String[] args) {
//        System.out.println(GetAuthToken.get(77274));
        WebSocketManager webSocketManager = WebSocketManager.getInstance(4404024);
        webSocketManager.init();
        webSocketManager.connect();
    }
}
