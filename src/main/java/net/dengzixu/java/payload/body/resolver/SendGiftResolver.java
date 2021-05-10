package net.dengzixu.java.payload.body.resolver;

import net.dengzixu.java.payload.body.Body;
import net.dengzixu.java.payload.body.FansMedalBody;
import net.dengzixu.java.payload.body.SendGiftBody;
import net.dengzixu.java.payload.constant.BodyType;

import java.util.Map;

public class SendGiftResolver extends Resolver {
    public SendGiftResolver(Map<String, Object> payloadMap) {
        super(payloadMap);
    }

    @Override
    public Body resolve() {
        Body body = new Body();

        SendGiftBody sendGiftBody = new SendGiftBody();

        Map<String, Object> dataMap = (Map<String, Object>) payloadMap.get("data");

        try {
            sendGiftBody.setUname((String) dataMap.get("uname"));
            sendGiftBody.setUid((int) dataMap.get("uid"));

            sendGiftBody.setCoinType((String) dataMap.get("coin_type"));
            sendGiftBody.setGiftId((int) dataMap.get("giftId"));
            sendGiftBody.setGiftName((String) dataMap.get("giftName"));
            sendGiftBody.setGiftType((int) dataMap.get("giftType"));
            sendGiftBody.setNum((int) dataMap.get("num"));

            sendGiftBody.setFansMedalBody(new FansMedalBody());

        } catch (Exception e) {
            e.printStackTrace();
        }

        body.setType(BodyType.SEND_GIFT);
        body.setBodyClass(sendGiftBody.getClass());
        body.setBody(sendGiftBody);

        return body;
    }
}
