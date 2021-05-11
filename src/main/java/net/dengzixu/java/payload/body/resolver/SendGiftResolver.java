package net.dengzixu.java.payload.body.resolver;

import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.payload.body.Body;
import net.dengzixu.java.payload.body.FansMedal;
import net.dengzixu.java.payload.body.SendGiftBody;
import net.dengzixu.java.payload.body.UserInfo;
import net.dengzixu.java.payload.constant.BodyType;

import java.util.Map;

public class SendGiftResolver extends BodyResolver {
    private static final String CMD = BodyType.SEND_GIFT;

    public SendGiftResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!CMD.equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Body resolve() {
        Body body = new Body();

        SendGiftBody sendGiftBody = new SendGiftBody();

        final Map<String, Object> dataMap = (Map<String, Object>) payloadMap.get("data");

        try {
            sendGiftBody.setUserInfo(new UserInfo() {{
                setUsername((String) dataMap.get("uname"));
                setUid((int) dataMap.get("uid"));
                setFace((String) dataMap.get("face"));
            }});

            sendGiftBody.setCoinType((String) dataMap.get("coin_type"));
            sendGiftBody.setGiftId((int) dataMap.get("giftId"));
            sendGiftBody.setGiftName((String) dataMap.get("giftName"));
            sendGiftBody.setGiftType((int) dataMap.get("giftType"));
            sendGiftBody.setNum((int) dataMap.get("num"));
            sendGiftBody.setPrice((int) dataMap.get("price"));

            // 粉丝牌信息
            final Map<String, Object> fansMedalMap = (Map<String, Object>) dataMap.get("medal_info");

            if ((int) fansMedalMap.get("target_id") != 0) {
                sendGiftBody.setFansMedal(new FansMedal() {{
                    setMedalLevel((int) fansMedalMap.get("medal_level"));
                    setMedalName((String) fansMedalMap.get("medal_name"));
                    setMedalColor((int) fansMedalMap.get("medal_color"));
                    setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
                    setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
                    setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
                    setLighted((int) fansMedalMap.get("is_lighted") == 1);
                }});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        body.setType(BodyType.SEND_GIFT);
        body.setBodyClass(sendGiftBody.getClass());
        body.setBody(sendGiftBody);

        return body;
    }
}
