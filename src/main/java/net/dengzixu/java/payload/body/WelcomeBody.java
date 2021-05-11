package net.dengzixu.java.payload.body;

public class WelcomeBody {
    private UserInfo userInfo;
    private FansMedal fansMedal;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public FansMedal getFansMedal() {
        return fansMedal;
    }

    public void setFansMedal(FansMedal fansMedal) {
        this.fansMedal = fansMedal;
    }

    @Override
    public String toString() {
        return "WelcomeBody{" +
                "userInfo=" + userInfo +
                ", fansMedal=" + fansMedal +
                '}';
    }
}
