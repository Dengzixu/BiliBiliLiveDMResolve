package net.dengzixu.java;

import net.dengzixu.java.constant.BodyCommand;
import net.dengzixu.java.constant.PacketProtocolVersion;
import org.junit.Assert;
import org.junit.Test;

public class BodyCommandTest {

    @Test
    public void BodyCommandTest1() {
        var result = BodyCommand.valueOf("DANMU_MSG", false);
        Assert.assertEquals(BodyCommand.DANMU_MSG, result);
    }

    @Test
    public void BodyCommandTest2() {
        var result = BodyCommand.valueOf("FUCK", false);
        Assert.assertEquals(BodyCommand.UNKNOWN, result);
    }
}
