package net.dengzixu.java.constant;

import java.util.HashMap;
import java.util.Map;

public enum PacketOperation {
    OPERATION_2(2),
    OPERATION_3(3),
    OPERATION_5(5),
    OPERATION_7(7),
    OPERATION_8(8),

    OPERATION_UNKNOWN(-1);

    private final Integer packetOperation;

    private static final Map<Integer, PacketOperation> enumMap = new HashMap<>();

    static {
        for (PacketOperation e : values()) {
            enumMap.put(e.operation(), e);
        }
    }

    PacketOperation(Integer operation) {
        this.packetOperation = operation;
    }

    public static PacketOperation valueOf(int operation) {
        return null == enumMap.get(operation) ? OPERATION_UNKNOWN : enumMap.get(operation);
    }

    public Integer operation() {
        return this.packetOperation;
    }

    @Override
    public String toString() {
        return String.valueOf(this.packetOperation);
    }
}
