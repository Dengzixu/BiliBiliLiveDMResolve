package net.dengzixu.java.body.resolver;

import net.dengzixu.java.message.FansMedal;
import net.dengzixu.java.message.UserInfo;
import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.message.Message;

import java.util.HashMap;
import java.util.Map;

public class InteractWordResolver extends BodyResolver {
    private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.INTERACT_WORD;

    public InteractWordResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Message resolve() {
        Message message = new Message();
        message.setBodyCommand(BODY_COMMAND);

        final Map<String, Object> dataMap = (Map<String, Object>) payloadMap.get("data");

        message.setContent(new HashMap<>() {{
            put("msg_type", dataMap.get("msg_type"));
        }});

        // 用户信息
        message.setUserInfo(new UserInfo() {{
            setUid((int) dataMap.get("uid"));
            setUsername((String) dataMap.get("uname"));
        }});

        // 粉丝牌信息
        final Map<String, Object> fansMedalMap = (Map<String, Object>) dataMap.get("fans_medal");
        if ((int) fansMedalMap.get("target_id") != 0) {
            message.setFansMedal(new FansMedal() {{
                setMedalLevel((int) fansMedalMap.get("medal_level"));
                setMedalName((String) fansMedalMap.get("medal_name"));
                setMedalColor((int) fansMedalMap.get("medal_color"));
                setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
                setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
                setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
                setLighted((int) fansMedalMap.get("is_lighted") == 1);
            }});
        }

        return message;
    }
}
