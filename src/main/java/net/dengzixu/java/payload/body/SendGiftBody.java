package net.dengzixu.java.payload.body;

public class SendGiftBody {
    private String coinType;
    private int giftId;
    private String giftName;
    private int giftType;
    private int num;
    private int price;

    private UserInfo userInfo;
    private FansMedal fansMedal;

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftType() {
        return giftType;
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

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
        return "SendGiftBody{" +
                "coinType='" + coinType + '\'' +
                ", giftId=" + giftId +
                ", giftName='" + giftName + '\'' +
                ", giftType=" + giftType +
                ", num=" + num +
                ", price=" + price +
                ", userInfo=" + userInfo +
                ", fansMedal=" + fansMedal +
                '}';
    }
}
