package net.dengzixu.java.payload.body.resolver;

import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.payload.body.Body;
import net.dengzixu.java.payload.body.FansMedalBody;
import net.dengzixu.java.payload.body.WelcomeBody;
import net.dengzixu.java.payload.constant.BodyType;

import java.util.Map;

public class WelcomeBodyResolver extends BodyResolver {
    private static final String CMD = BodyType.INTERACT_WORD;

    public WelcomeBodyResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!CMD.equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Body resolve() {
        Body body = new Body();

        WelcomeBody welcomeBody = new WelcomeBody();

        // 用户信息
        Map<String, Object> dataMap = (Map<String, Object>) payloadMap.get("data");
        welcomeBody.setUid((int) dataMap.get("uid"));
        welcomeBody.setUsername((String) dataMap.get("uname"));

        // 粉丝牌信息
        Map<String, Object> fansMedalMap = (Map<String, Object>) dataMap.get("fans_medal");

        if ((int) fansMedalMap.get("target_id") != 0) {
            FansMedalBody fansMedalBody = new FansMedalBody();
            fansMedalBody.setMedalLevel((int) fansMedalMap.get("medal_level"));
            fansMedalBody.setMedalName((String) fansMedalMap.get("medal_name"));
            fansMedalBody.setMedalColor((int) fansMedalMap.get("medal_color"));
            fansMedalBody.setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
            fansMedalBody.setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
            fansMedalBody.setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
            fansMedalBody.setLighted((int) fansMedalMap.get("is_lighted") == 1);
            welcomeBody.setFansMedalBody(fansMedalBody);
        }

        body.setType(BodyType.INTERACT_WORD);
        body.setBodyClass(welcomeBody.getClass());
        body.setBody(welcomeBody);

        return body;
    }
}
