package net.dengzixu.java.payload.body.resolver;

import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.payload.body.Body;
import net.dengzixu.java.payload.body.DanmuBody;
import net.dengzixu.java.payload.body.FansMedal;
import net.dengzixu.java.payload.body.UserInfo;
import net.dengzixu.java.payload.constant.BodyType;

import java.util.ArrayList;
import java.util.Map;

public class DanmuBodyResolver extends BodyResolver {
    private static final String CMD = BodyType.DANMU_MSG;

    public DanmuBodyResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!CMD.equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }


    @Override
    public Body resolve() {
        Body body = new Body();

        DanmuBody danmuBody = new DanmuBody();

        // ["info"]
        final ArrayList<Object> infoList = (ArrayList) this.payloadMap.get("info");

        // 弹幕内容
        danmuBody.setDanmu((String) infoList.get(1));

        // 发送用户
        final ArrayList<Object> userInfoList = (ArrayList) infoList.get(2);
        danmuBody.setUserInfo(new UserInfo() {{
            setUid((int) userInfoList.get(0));
            setUsername((String) userInfoList.get(1));
        }});


        // 发送用户粉丝牌信息
        final ArrayList<Object> fansMedalList = (ArrayList) infoList.get(3);

        if (!fansMedalList.isEmpty()) {
            danmuBody.setFansMedal(new FansMedal() {{
                setMedalLevel((int) fansMedalList.get(0));
                setMedalName((String) fansMedalList.get(1));
                setMedalColor((int) fansMedalList.get(4));
                setMedalColorBorder((int) fansMedalList.get(7));
                setMedalColorStart((int) fansMedalList.get(8));
                setMedalColorEnd((int) fansMedalList.get(9));
                setLighted((int) fansMedalList.get(11) == 1);

            }});
        }

        body.setType(BodyType.DANMU_MSG);
        body.setBodyClass(danmuBody.getClass());
        body.setBody(danmuBody);

        return body;
    }
}
