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
            }});

            sendGiftBody.setCoinType((String) dataMap.get("coin_type"));
            sendGiftBody.setGiftId((int) dataMap.get("giftId"));
            sendGiftBody.setGiftName((String) dataMap.get("giftName"));
            sendGiftBody.setGiftType((int) dataMap.get("giftType"));
            sendGiftBody.setNum((int) dataMap.get("num"));

            sendGiftBody.setFansMedal(new FansMedal() {{

            }});
        } catch (Exception e) {
            e.printStackTrace();
        }

        body.setType(BodyType.SEND_GIFT);
        body.setBodyClass(sendGiftBody.getClass());
        body.setBody(sendGiftBody);

        return body;
    }
}
