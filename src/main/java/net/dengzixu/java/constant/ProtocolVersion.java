package net.dengzixu.java.constant;

public enum ProtocolVersion {

    PROTOCOL_VERSION_0(0),
    PROTOCOL_VERSION_1(1),
    PROTOCOL_VERSION_2(2);


    private final Integer protocolVersion;

    ProtocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Integer version() {
        return this.version();
    }
}
