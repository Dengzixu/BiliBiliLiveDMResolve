package net.dengzixu.java.protocol;

import net.dengzixu.java.packet.Packet;
import net.dengzixu.java.utils.ZlibUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PacketResolve {
    private final byte[] rawData;
    private final int rawDataLength;

    private int currentOffset;

    private List<Packet> resultPacketList = new ArrayList<>();

    public PacketResolve(byte[] rawData) {
        if (null == rawData) {
            throw new NullPointerException();
        }
        this.rawData = rawData;
        this.rawDataLength = rawData.length;
    }

    public List<Packet> getPacketList() {
//        System.out.println("Raw Packet length: " + rawDataLength);

        // 根据传入的数据分配空间
        final ByteBuffer byteBuffer = ByteBuffer.allocate(rawDataLength);

        // 数据装入
        byteBuffer.put(rawData);

        // 需要判断一下数据包里是不是只有一条数据
        while (currentOffset < rawDataLength) {
            Packet resolvedPacket = new Packet() {{
                setPacketLength(byteBuffer.getInt(currentOffset));
                setHeaderLength(byteBuffer.getShort(currentOffset + 4));
                setProtocolVersion(byteBuffer.getShort(currentOffset + 6));
                setOperation(byteBuffer.getInt(currentOffset + 8));
                setSequenceId(byteBuffer.getInt(currentOffset + 12));
                byte[] bodyBytes = new byte[getPacketLength() - 16];

                for (int i = 0; i < bodyBytes.length; i++) {
//                    System.out.println("[" + i + "]");
                    bodyBytes[i] = byteBuffer.get(currentOffset + 16 + i);
                }

                setPayload(bodyBytes);
            }};
            resultPacketList.add(resolvedPacket);
            currentOffset += resolvedPacket.getPacketLength();
        }

        // TODO 猜测 如果大于 1 个数据包 数据包就不可能是压缩的
        if (resultPacketList.size() > 1) {
            return resultPacketList;
        }

        // 根据 Protocol Version 进行处理
        switch (resultPacketList.get(0).getProtocolVersion()) {
            // 如果协议版本为 0 或 1 直接返回
            case BiliBiliLiveDMProtocol.PROTOCOL_VERSION_0:
            case BiliBiliLiveDMProtocol.PROTOCOL_VERSION_1:
                break;
            // 如果协议版本为 2 就解压一下
            case BiliBiliLiveDMProtocol.PROTOCOL_VERSION_2:
                byte[] compressedData = ZlibUtil.inflate(resultPacketList.get(0).getPayload());
//                System.out.println(Arrays.toString(compressedData));
                // 递归一把梭
                resultPacketList = new PacketResolve(compressedData).getPacketList();
                break;
            default:
                resultPacketList = null;
        }
        return resultPacketList;
    }
}
