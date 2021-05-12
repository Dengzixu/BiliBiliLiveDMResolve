package net.dengzixu.java.constant;

public enum PacketProtocolVersion {

    PROTOCOL_VERSION_0(0),
    PROTOCOL_VERSION_1(1),
    PROTOCOL_VERSION_2(2);

    private final Integer packetProtocolVersion;

    PacketProtocolVersion(Integer packetProtocolVersion) {
        this.packetProtocolVersion = packetProtocolVersion;
    }

    public Integer version() {
        return this.packetProtocolVersion;
    }

    @Override
    public String toString() {
        return String.valueOf(this.packetProtocolVersion);
    }
}
