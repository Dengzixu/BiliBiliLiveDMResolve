package net.dengzixu.java.exception;

public class ErrorCmdException extends RuntimeException {
    public ErrorCmdException() {
        super("Unknown Cmd Type");
    }
}
