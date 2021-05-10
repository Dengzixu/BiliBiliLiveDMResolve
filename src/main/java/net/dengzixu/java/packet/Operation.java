package net.dengzixu.java.packet;

/**
 * 操作类型
 * 2: 客户端   (空)                 心跳      不发送心跳包，70 秒之后会断开连接，通常每 30 秒发送 1 次
 * 3: 服务器   Int 32 Big Endian   心跳回应    Body 内容为房间人气值
 * 5: 服务器   JSON                通知       弹幕、广播等全部信息
 * 7: 客户端   JSON                进房       WebSocket 连接成功后的发送的第一个数据包，发送要进入房间 ID
 * 8: 服务器   (空)                进房回应
 */
public class Operation {
    public static final byte OPERATION_2 = 2;
    public static final byte OPERATION_3 = 3;
    public static final byte OPERATION_5 = 5;
    public static final byte OPERATION_7 = 7;
    public static final byte OPERATION_8 = 8;
}
