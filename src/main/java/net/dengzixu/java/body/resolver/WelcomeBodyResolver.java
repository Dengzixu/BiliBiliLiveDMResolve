package net.dengzixu.java.body.resolver;

import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.body.Body;
import net.dengzixu.java.body.FansMedal;
import net.dengzixu.java.body.UserInfo;
import net.dengzixu.java.body.WelcomeBody;
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
        final Map<String, Object> dataMap = (Map<String, Object>) payloadMap.get("data");
        welcomeBody.setUserInfo(new UserInfo() {{
            setUid((int) dataMap.get("uid"));
            setUsername((String) dataMap.get("uname"));
        }});

        // 粉丝牌信息
        final Map<String, Object> fansMedalMap = (Map<String, Object>) dataMap.get("fans_medal");

        if ((int) fansMedalMap.get("target_id") != 0) {
            welcomeBody.setFansMedal(new FansMedal() {{
                setMedalLevel((int) fansMedalMap.get("medal_level"));
                setMedalName((String) fansMedalMap.get("medal_name"));
                setMedalColor((int) fansMedalMap.get("medal_color"));
                setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
                setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
                setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
                setLighted((int) fansMedalMap.get("is_lighted") == 1);
            }});
        }

        body.setType(BodyType.INTERACT_WORD);
        body.setBodyClass(welcomeBody.getClass());
        body.setBody(welcomeBody);

        return body;
    }
}
