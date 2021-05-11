package net.dengzixu.java.payload.body.resolver;

import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.payload.body.Body;

import java.util.Map;

public abstract class BodyResolver {
    protected Map<String, Object> payloadMap;
    protected String payloadCmd;

    public BodyResolver(Map<String, Object> payloadMap) {
        this.payloadMap = payloadMap;

        if (null == payloadMap) {
            throw new NullPointerException();
        }

        try {
            payloadCmd = (String) payloadMap.get("cmd");
        } catch (Exception e) {
            throw new ErrorCmdException();
        }

        if (null == payloadCmd) {
            throw new ErrorCmdException();
        }
    }

    public abstract Body resolve();
}
