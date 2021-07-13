package net.dengzixu.java;

import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.listener.Listener;
import net.dengzixu.java.message.Message;

public class DanmuResolverTest {
    public static void main(String[] args) {
        DanmuResolver danmuResolver = DanmuResolver.getInstance(14047);

        danmuResolver.addListener(new Listener() {
            @Override
            public void onMessage(Message message) {
                if (message.getBodyCommand().equals(BodyCommandEnum.INTERACT_WORD)) {
                    if (null != message.getFansMedal() && message.getFansMedal().isLighted()) {
                        System.out.printf("欢迎 [%s][%s-%d] 进入房间\n", message.getUserInfo().getUsername(),
                                message.getFansMedal().getMedalName(),
                                message.getFansMedal().getMedalLevel());
                    } else {
                        System.out.printf("欢迎 [%s] 进入房间\n", message.getUserInfo().getUsername());
                    }
                }
            }
        });

        danmuResolver.addListener(new Listener() {
            @Override
            public void onMessage(Message message) {
                if (message.getBodyCommand().equals(BodyCommandEnum.DANMU_MSG)) {
                    if (null != message.getFansMedal() && message.getFansMedal().isLighted()) {
                        System.out.printf("[%s][%s-%d] %s\n", message.getUserInfo().getUsername(),
                                message.getFansMedal().getMedalName(),
                                message.getFansMedal().getMedalLevel(),
                                message.getContent().get("danmu"));
                    } else {
                        System.out.printf("[%s] %s\n", message.getUserInfo().getUsername(), message.getContent().get("danmu"));
                    }
                }
            }
        });
    }
}
