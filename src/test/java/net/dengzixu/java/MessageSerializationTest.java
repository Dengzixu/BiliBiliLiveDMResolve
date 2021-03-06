package net.dengzixu.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.message.FansMedal;
import net.dengzixu.java.message.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class MessageSerializationTest {
    private Message message;

    @Before
    public void init() {
        message = new Message() {{
            setContent(new HashMap<String, Object>());
            setBodyCommand(BodyCommandEnum.DANMU_MSG);
            setFansMedal(new FansMedal());
            setUserInfo(new UserInfo());
        }};
    }

    @Test
    public void test() {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }
}
