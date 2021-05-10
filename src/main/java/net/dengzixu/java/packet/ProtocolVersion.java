package net.dengzixu.java.packet;

/**
 * 协议版本
 * 0: JSON 	            JSON纯文本，可以直接通过 JSON.stringify 解析
 * 1: Int 32 Big Endian Body 内容为房间人气值
 * 2: Buffer            压缩过的 Buffer，Body 内容需要用zlib.inflate解压出一个新的数据包，然后从数据包格式那一步重新操作一遍
 */
public class ProtocolVersion {
    public static final short PROTOCOL_VERSION_0 = 0;
    public static final short PROTOCOL_VERSION_1 = 1;
    public static final short PROTOCOL_VERSION_2 = 2;
}
