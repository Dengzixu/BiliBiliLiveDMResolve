package net.dengzixu.java.constant;

public enum BodyCommand {
    AUTH_SUCCESS("AUTH_SUCCESS"),
    DANMU_MSG("DANMU_MSG"),
    INTERACT_WORD("INTERACT_WORD"),
    POPULARITY("POPULARITY"),
    SEND_GIFT("SEND_GIFT"),
    STOP_LIVE_ROOM_LIST("STOP_LIVE_ROOM_LIST"),

    UNKNOWN("UNKNOWN");

    public final String command;

    BodyCommand(String command) {
        this.command = command;
    }

    public String command() {
        return command;
    }

    @Override
    public String toString() {
        return command;
    }
}
