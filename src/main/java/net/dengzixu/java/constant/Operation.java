package net.dengzixu.java.constant;

public enum Operation {
    OPERATION_2(2),
    OPERATION_3(3),
    OPERATION_5(5),
    OPERATION_7(7),
    OPERATION_8(8);


    private final Integer operation;


    Operation(Integer operation) {
        this.operation = operation;
    }

    public Integer operation() {
        return this.operation;
    }
}
