package net.dengzixu.java.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @author Deng Zixu <admin@dengzixu.com>
 * <a>https://github.com/lovelyyoshino/Bilibili-Live-API/blob/master/API.WebSocket.md</a>
 */
public class BiliBiliLiveDMProtocol {
    /**
     * 数据包头部长度
     * 固定为 16
     */
    private static final short HEAD_LENGTH = 16;

    /**
     * 数据包头部长度
     * 固定为 1
     */
    private static final byte SEQUENCE_ID = 1;

    /**
     * 协议版本
     * 0: JSON 	            JSON纯文本，可以直接通过 JSON.stringify 解析
     * 1: Int 32 Big Endian Body 内容为房间人气值
     * 2: Buffer            压缩过的 Buffer，Body 内容需要用zlib.inflate解压出一个新的数据包，然后从数据包格式那一步重新操作一遍
     */
    public static final short PROTOCOL_VERSION_0 = 0;
    public static final short PROTOCOL_VERSION_1 = 1;
    public static final short PROTOCOL_VERSION_2 = 2;

    /**
     * 操作类型
     * 2: 客户端   (空)                 心跳      不发送心跳包，70 秒之后会断开连接，通常每 30 秒发送 1 次
     * 3: 服务器   Int 32 Big Endian   心跳回应    Body 内容为房间人气值
     * 5: 服务器   JSON                通知       弹幕、广播等全部信息
     * 7: 客户端   JSON                进房       WebSocket 连接成功后的发送的第一个数据包，发送要进入房间 ID
     * 8: 服务器   (空)                进房回应
     */
    public static final byte OPERATION_2 = 2;
    public static final byte OPERATION_3 = 3;
    public static final byte OPERATION_5 = 4;
    public static final byte OPERATION_7 = 7;
    public static final byte OPERATION_8 = 8;

    public byte[] buildPacket() {
        return buildPacket(0, (short) 0, (short) 0, 0, 0, null);
    }

    public byte[] buildPacket(int Operation, short protocolVersion) {
        return buildPacket(HEAD_LENGTH,
                HEAD_LENGTH,
                protocolVersion,
                Operation,
                SEQUENCE_ID,
                null
        );
    }

    public byte[] buildPacket(int Operation, short protocolVersion, String body) {
        return buildPacket(HEAD_LENGTH + body.length(),
                HEAD_LENGTH,
                protocolVersion,
                Operation,
                SEQUENCE_ID,
                body
        );
    }

    /**
     * 构造数据包
     *
     * @param packetLength    数据包长度
     *                        数据包长度 = 数据包头部长度 + 数据内容长度
     * @param headerLength    数据包头部长度 (固定为 16)
     * @param protocolVersion 协议版本
     * @param Operation       操作类型
     * @param sequenceId      数据包头部长度 (固定为 1)
     * @param body            数据包 body
     * @return 数据包
     */
    public byte[] buildPacket(int packetLength, short headerLength, short protocolVersion,
                              int Operation, int sequenceId, String body) {
        ByteBuffer head = ByteBuffer.allocate(packetLength);

        try {
            // Packet Length
            head.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(packetLength).array());

            // Header Length
            head.put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(HEAD_LENGTH).array());

            // Protocol Version
            head.put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(PROTOCOL_VERSION_2).array());

            // Operation
            head.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(OPERATION_7).array());

            // Operation
            head.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(SEQUENCE_ID).array());

            if (null != body) {
                head.put(body.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return head.array();
    }
}
