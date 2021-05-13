package net.dengzixu.java.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dengzixu.java.body.resolver.*;
import net.dengzixu.java.constant.BodyCommand;
import net.dengzixu.java.constant.PacketOperation;
import net.dengzixu.java.message.Message;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PayloadResolver {
    private final byte[] payload;
    private final PacketOperation operation;

    public PayloadResolver(@NotNull String payload, PacketOperation packetOperation) {
        this(payload.getBytes(StandardCharsets.UTF_8), packetOperation);
    }

    public PayloadResolver(byte[] payload, PacketOperation packetOperation) {
        this.payload = payload;
        this.operation = packetOperation;
    }

    public Message resolve() {
        Message message = new Message();

        switch (operation) {
            case OPERATION_3: {
                ByteBuffer byteBuffer = ByteBuffer.allocate(payload.length).put(payload);
                message.setBodyCommand(BodyCommand.POPULARITY);
                message.setContent(new HashMap<>() {{
                    put("popularity", byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt(0));
                }});

                break;
            }
            case OPERATION_5: {
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

                switch (BodyCommand.valueOf((String) payloadMap.get("cmd"), false)) {
                    case DANMU_MSG:
                        bodyResolver = new DanmuBodyResolver(payloadMap);
                        break;
                    case INTERACT_WORD:
                        bodyResolver = new WelcomeBodyResolver(payloadMap);
                        break;
                    case SEND_GIFT:
                        bodyResolver = new SendGiftResolver(payloadMap);
                        break;
                    // 不需要处理的和未知类型
                    case STOP_LIVE_ROOM_LIST:
                    default:
                        bodyResolver = new UnknownBodyResolver(null);
                }
                try {
                    message = bodyResolver.resolve();
                } catch (Exception e) {
                    message = new Message() {{
                        setBodyCommand(BodyCommand.UNKNOWN);
                    }};
                    e.printStackTrace();
                }
                break;
            }
            case OPERATION_8: {
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
                        message = new Message() {{
                            setBodyCommand(BodyCommand.AUTH_SUCCESS);
                        }};
                    }
                } catch (Exception ignored) {
                }
                break;
            }
            default:
                message = new Message() {{
                    setBodyCommand(BodyCommand.UNKNOWN);
                }};

        }
        return message;
    }
}
