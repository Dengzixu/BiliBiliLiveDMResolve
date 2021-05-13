package net.dengzixu.java;

import net.dengzixu.java.constant.PacketOperationEnum;
import org.junit.Assert;
import org.junit.Test;

public class PacketOperationEnumTest {

    @Test
    public void testOperation7() {
        var result = PacketOperationEnum.getEnum(7);
        Assert.assertEquals(PacketOperationEnum.OPERATION_7, result);
    }

    @Test
    public void testOperationUnknown() {
        var result = PacketOperationEnum.getEnum(12);
        Assert.assertEquals(PacketOperationEnum.OPERATION_UNKNOWN, result);
    }
}
