package net.dengzixu.java.filter;

import net.dengzixu.java.message.Message;

public interface Filter {
    void doFilter(Message message);
}
