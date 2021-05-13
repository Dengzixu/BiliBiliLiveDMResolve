package net.dengzixu.java;

import net.dengzixu.java.constant.PacketOperation;
import org.junit.Assert;
import org.junit.Test;

public class PacketOperationTest {

    @Test
    public void testOperation7() {
        var result = PacketOperation.valueOf(7);
        Assert.assertEquals(PacketOperation.OPERATION_7, result);
    }

    @Test
    public void testOperationUnknown() {
        var result = PacketOperation.valueOf(12);
        Assert.assertEquals(PacketOperation.OPERATION_UNKNOWN, result);
    }
}
