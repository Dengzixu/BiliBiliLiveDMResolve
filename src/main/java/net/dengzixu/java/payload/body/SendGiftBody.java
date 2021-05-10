package net.dengzixu.java.payload.body;

public class SendGiftBody {
    private String uname;
    private long uid;

    private String coinType;
    private int giftId;
    private String giftName;
    private int giftType;
    private int num;
    private int price;

    private FansMedalBody fansMedalBody;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

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

    public FansMedalBody getFansMedalBody() {
        return fansMedalBody;
    }

    public void setFansMedalBody(FansMedalBody fansMedalBody) {
        this.fansMedalBody = fansMedalBody;
    }

    @Override
    public String toString() {
        return "SendGiftBody{" +
                "uname='" + uname + '\'' +
                ", uid=" + uid +
                ", coinType='" + coinType + '\'' +
                ", giftId=" + giftId +
                ", giftName='" + giftName + '\'' +
                ", giftType=" + giftType +
                ", num=" + num +
                ", price=" + price +
                ", fansMedalBody=" + fansMedalBody +
                '}';
    }
}
