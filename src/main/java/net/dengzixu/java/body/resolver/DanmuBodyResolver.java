package net.dengzixu.java.body.resolver;

import net.dengzixu.java.cache.FaceCache;
import net.dengzixu.java.message.FansMedal;
import net.dengzixu.java.message.UserInfo;
import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.third.api.AccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DanmuBodyResolver extends BodyResolver {
    private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.DANMU_MSG;

    Logger logger = LoggerFactory.getLogger(DanmuBodyResolver.class);

    public DanmuBodyResolver(Map<String, Object> payloadMap) {
        super(payloadMap);

        if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
            throw new ErrorCmdException();
        }
    }

    @Override
    public Message resolve() {
        Message message = new Message();
        message.setBodyCommand(BODY_COMMAND);

        // ["info"] 弹幕内容
        final ArrayList<Object> infoList = (ArrayList) this.payloadMap.get("info");

        message.setContent(new HashMap<>() {{
            put("danmu", infoList.get(1));
        }});

        // 用户信息
        final ArrayList<Object> userInfoList = (ArrayList) infoList.get(2);
        message.setUserInfo(new UserInfo() {{
            setUid((int) userInfoList.get(0));
            setUsername((String) userInfoList.get(1));
        }});

        // 用户头像获取
        // 用户头像缓存
        FaceCache faceCache = FaceCache.getInstance();

        // 判断缓存里是否存在头像
        if (faceCache.get(message.getUserInfo().getUid()) == null) {
            // 当缓存中不存在投降时，从服务器中获取
            new Thread(() -> {
                String face = new AccountInfo().getFace(message.getUserInfo().getUid());

                FaceCache.getInstance().put(message.getUserInfo().getUid(), face);
            }).start();
        } else {
            message.getUserInfo().setFace(faceCache.get(message.getUserInfo().getUid()));
        }


        // 粉丝牌信息
        final ArrayList<Object> fansMedalList = (ArrayList) infoList.get(3);
        if (!fansMedalList.isEmpty()) {
            message.setFansMedal(new FansMedal() {{
                setMedalLevel((int) fansMedalList.get(0));
                setMedalName((String) fansMedalList.get(1));
                setMedalColor((int) fansMedalList.get(4));
                setMedalColorBorder((int) fansMedalList.get(7));
                setMedalColorStart((int) fansMedalList.get(8));
                setMedalColorEnd((int) fansMedalList.get(9));
                setLighted((int) fansMedalList.get(11) == 1);
            }});
        }

        return message;
    }
}
