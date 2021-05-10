package net.dengzixu.java.payload.body;

public class WelcomeBody {
    private String username;
    private long uid;

    private FansMedalBody fansMedalBody;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public FansMedalBody getFansMedalBody() {
        return fansMedalBody;
    }

    public void setFansMedalBody(FansMedalBody fansMedalBody) {
        this.fansMedalBody = fansMedalBody;
    }

    @Override
    public String toString() {
        return "WelcomeBody{" +
                "username='" + username + '\'' +
                ", uid=" + uid +
                ", fansMedalBody=" + fansMedalBody +
                '}';
    }
}
