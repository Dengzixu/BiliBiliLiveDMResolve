package net.dengzixu.java;

import net.dengzixu.java.constant.PacketOperation;
import net.dengzixu.java.constant.PacketProtocolVersion;
import org.junit.Assert;
import org.junit.Test;

public class PacketProtocolVersionTest {

    @Test
    public void testPacketProtocolVersion2() {
        var result = PacketProtocolVersion.valueOf(1);
        Assert.assertEquals(PacketProtocolVersion.PROTOCOL_VERSION_1, result);
    }

    @Test
    public void testPacketProtocolVersionUnknown() {
        var result = PacketProtocolVersion.valueOf(10);
        Assert.assertEquals(PacketProtocolVersion.PROTOCOL_VERSION_UNKNOWN, result);
    }
}
