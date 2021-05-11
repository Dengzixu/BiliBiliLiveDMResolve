package net.dengzixu.java.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.java.packet.Operation;
import net.dengzixu.java.payload.body.Body;
import net.dengzixu.java.payload.body.resolver.*;
import net.dengzixu.java.payload.constant.BodyType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PayloadResolver {
    private final byte[] payload;
    private final int operation;

    public PayloadResolver(@NotNull String payload, int operation) {
        this(payload.getBytes(StandardCharsets.UTF_8), operation);
    }

    public PayloadResolver(byte[] payload, int operation) {
        this.payload = payload;
        this.operation = operation;
    }

    public Body resolve() {
        Body result = new Body();

        switch (operation) {
            case Operation.OPERATION_3: {
                ByteBuffer byteBuffer = ByteBuffer.allocate(payload.length).put(payload);
                result.setBody(byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt(0));
                result.setBodyClass(int.class);
                result.setType(BodyType.POPULARITY);

                break;
            }
            case Operation.OPERATION_5: {
                Map<String, Object> payloadMap;
                try {
                    // 反序列化
                    payloadMap = new ObjectMapper().readValue(new String(payload), Map.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    break;
                }

                // 根据 cmd 解析
                BodyResolver bodyResolver;

                switch ((String) payloadMap.get("cmd")) {
                    case BodyType.DANMU_MSG:
                        bodyResolver = new DanmuBodyResolver(payloadMap);
                        break;
                    case BodyType.INTERACT_WORD:
                        bodyResolver = new WelcomeBodyResolver(payloadMap);
                        break;
                    case BodyType.SEND_GIFT:
                        System.out.println("FLAG0");
                        bodyResolver = new SendGiftResolver(payloadMap);
                        break;
                    // 不需要处理的和未知类型
                    case BodyType.STOP_LIVE_ROOM_LIST:
                    default:
                        bodyResolver = new UnknownBodyResolver(null);

                }
                result = bodyResolver.resolve();
                break;
            }
            case Operation.OPERATION_8: {
                Map<String, Object> payloadMap;
                try {
                    // 反序列化
                    payloadMap = new ObjectMapper().readValue(new String(payload), Map.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    break;
                }
                // 特殊处理
                try {
                    if ((int) payloadMap.get("code") == 0) {
                        result = new Body() {{
                            setType(BodyType.AUTH_SUCCESS);
                            setBody(true);
                            setBodyClass(boolean.class);
                        }};
                    }
                } catch (Exception ignored) {
                }
                break;
            }
            default:
                result = new Body() {{
                    setType(BodyType.UNKNOWN);
                    setBodyClass(null);
                    setBody(null);
                }};
        }
        return result;
    }
}
