package net.dengzixu.java.listener;

import net.dengzixu.java.message.Message;

public abstract class Listener {
    public abstract void onMessage(Message message);
}
