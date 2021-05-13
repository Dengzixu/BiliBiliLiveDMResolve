package net.dengzixu.java.body.resolver;

import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.message.Message;

import java.util.HashMap;
import java.util.Map;

public class UnknownBodyResolver extends BodyResolver {
    public UnknownBodyResolver(Map<String, Object> payloadMap) {
        super(new HashMap<String, Object>() {{
            put("cmd", "unknown");
        }});
    }

    @Override
    public Message resolve() {
        return new Message() {{
            setBodyCommand(BodyCommandEnum.UNKNOWN);
        }};
    }
}
