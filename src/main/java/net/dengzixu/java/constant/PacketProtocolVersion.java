package net.dengzixu.java.constant;

import java.util.HashMap;
import java.util.Map;

public enum PacketProtocolVersion {
    /**
     * 协议版本
     * 0: JSON 	            JSON纯文本，可以直接通过 JSON.stringify 解析
     * 1: Int 32 Big Endian Body 内容为房间人气值
     * 2: Buffer            压缩过的 Buffer，Body 内容需要用zlib.inflate解压出一个新的数据包，然后从数据包格式那一步重新操作一遍
     */
    PROTOCOL_VERSION_0((short) 0),
    PROTOCOL_VERSION_1((short) 1),
    PROTOCOL_VERSION_2((short) 2),

    PROTOCOL_VERSION_UNKNOWN((short) -1);

    private final Short packetProtocolVersion;

    private static final Map<Short, PacketProtocolVersion> enumMap = new HashMap<>();

    static {
        for (PacketProtocolVersion e : values()) {
            enumMap.put(e.version(), e);
        }
    }

    PacketProtocolVersion(Short packetProtocolVersion) {
        this.packetProtocolVersion = packetProtocolVersion;
    }

    public static PacketProtocolVersion valueOf(short version) {
        return null == enumMap.get(version) ? PROTOCOL_VERSION_UNKNOWN : enumMap.get(version);
    }

    public static PacketProtocolVersion valueOf(int version) {
        return valueOf((short) version);
    }

    public Short version() {
        return this.packetProtocolVersion;
    }

    @Override
    public String toString() {
        return String.valueOf(this.packetProtocolVersion);
    }
}
