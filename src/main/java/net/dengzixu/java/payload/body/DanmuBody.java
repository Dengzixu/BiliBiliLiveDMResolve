package net.dengzixu.java.payload.body;

public class DanmuBody {
    private String danmu;

    private UserInfo UserInfo;
    private FansMedal fansMedal;

    public String getDanmu() {
        return danmu;
    }

    public void setDanmu(String danmu) {
        this.danmu = danmu;
    }

    public net.dengzixu.java.payload.body.UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(net.dengzixu.java.payload.body.UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public FansMedal getFansMedal() {
        return fansMedal;
    }

    public void setFansMedal(FansMedal fansMedal) {
        this.fansMedal = fansMedal;
    }

    @Override
    public String toString() {
        return "DanmuBody{" +
                "danmu='" + danmu + '\'' +
                ", UserInfo=" + UserInfo +
                ", fansMedal=" + fansMedal +
                '}';
    }
}
