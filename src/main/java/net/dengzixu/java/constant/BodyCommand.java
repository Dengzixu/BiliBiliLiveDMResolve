package net.dengzixu.java.constant;

public enum BodyCommand {
    DANMU_MSG("DANMU_MSG"),
    INTERACT_WORD("INTERACT_WORD"),
    SEND_GIFT("SEND_GIFT"),
    STOP_LIVE_ROOM_LIST("STOP_LIVE_ROOM_LIST"),

    AUTH_SUCCESS("AUTH_SUCCESS"),
    POPULARITY("POPULARITY"),

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
