package net.dengzixu.java.body.resolver;

import net.dengzixu.java.body.Body;
import net.dengzixu.java.payload.constant.BodyType;

import java.util.HashMap;
import java.util.Map;

public class UnknownBodyResolver extends BodyResolver {
    public UnknownBodyResolver(Map<String, Object> payloadMap) {
        super(new HashMap<String, Object>() {{
            put("cmd", "unknown");
        }});
    }

    @Override
    public Body resolve() {
        return new Body() {{
            setType(BodyType.UNKNOWN);
            setBodyClass(null);
            setBody(null);
        }};
    }
}
