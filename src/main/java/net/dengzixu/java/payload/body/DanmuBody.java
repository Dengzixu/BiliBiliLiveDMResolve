package net.dengzixu.java.payload.body;

public class DanmuBody {
    private String danmu;
    private long uid;
    private String username;

    private FansMedalBody fansMedalBody;

    public String getDanmu() {
        return danmu;
    }

    public void setDanmu(String danmu) {
        this.danmu = danmu;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FansMedalBody getFansMedalBody() {
        return fansMedalBody;
    }

    public void setFansMedalBody(FansMedalBody fansMedalBody) {
        this.fansMedalBody = fansMedalBody;
    }

    @Override
    public String toString() {
        return "DanmuBody{" +
                "danmu='" + danmu + '\'' +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", fansMedalBody=" + fansMedalBody +
                '}';
    }
}
