package net.dengzixu.java;

import net.dengzixu.java.constant.PacketProtocolVersionEnum;
import org.junit.Assert;
import org.junit.Test;

public class PacketProtocolVersionTest {

    @Test
    public void testPacketProtocolVersion2() {
        var result = PacketProtocolVersionEnum.getEnum(1);
        Assert.assertEquals(PacketProtocolVersionEnum.PROTOCOL_VERSION_1, result);
    }

    @Test
    public void testPacketProtocolVersionUnknown() {
        var result = PacketProtocolVersionEnum.getEnum(10);
        Assert.assertEquals(PacketProtocolVersionEnum.PROTOCOL_VERSION_UNKNOWN, result);
    }
}
