package net.dengzixu.java.constant;

public enum PacketOperation {
    OPERATION_2(2),
    OPERATION_3(3),
    OPERATION_5(5),
    OPERATION_7(7),
    OPERATION_8(8);


    private final Integer packetOperation;

    PacketOperation(Integer operation) {
        this.packetOperation = operation;
    }

    public Integer operation() {
        return this.packetOperation;
    }

    @Override
    public String toString() {
        return String.valueOf(this.packetOperation);
    }
}
