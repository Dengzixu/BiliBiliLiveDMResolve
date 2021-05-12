package net.dengzixu.java;

import net.dengzixu.java.message.Message;
import net.dengzixu.java.packet.Packet;
import net.dengzixu.java.packet.PacketResolve;
import net.dengzixu.java.payload.PayloadResolver;
import org.apache.commons.codec.binary.Base64;

import java.util.List;

public class ResolveTest {
    public static void main(String[] args) {
        // 进入房间数据包 聚合 一条带牌子 一条不带牌子
        String demoBase64Data = "AAAB9AAQAAIAAAAFAAAAAHjatJJBixM/GMaHhf/hf/MzvOcckpk00+akqAcvCsuCB5GQnWS7gZlJSVJBloK4oF2hiBcRRFDw4KKIB8GCiF+m0939FpJuW3drFQR3DsNMnpc3z/PwS5KNy8mlJD7/xdceFJUCDjdubl3fvHJ1S9y+tXkNECgZJPA96BsFPKMsYzQnCPq1rDRwaB4fNB/fT74+ad4eNk+Hk/Ho6PD78f7z6Yc3MJ8ShS2tAw6AwChdBxOM9sDvkLsIKt8V4X5PAycInLVVvIZQTHMEwVTaB1n1gBNGOoTSnGUIfGGdPnvE8hzBjqy9qLSSZXQbpOvqIOKyVo5x2s5wC8FMFqW+p0vg6eJ/nuT486uTl1+a8TdYCHPfDLO83aLnToUP0gXgJGVZmrbYeVHX6nfStnVKu7Oq8aI03d2gFXCMoNuXTi08YgS+pwsTQ83qK2w9C4URyLrYtU4sOktT2mFZq7PsJ+t0BrPtvue0VPNl8VOYeseeLixsHZzZ7gdj69hb10mlgePBclZpX5zOBmlKER1EfZAkG///NT9pO2bOCP0J0HT47GT/3fThqPn0qBm+mIwPjl6PmuGDC6GH/koPJSn9Az14hRq8Qs0qLHgtJngNH3g9GPifEYGXYfFFgvAjAAD//5JtZw0=";

        // 进入房间数据包 带牌子
//        String demoBase64Data = "AAABgAAQAAIAAAAFAAAAAHjabJCxihUxFIYvgr3PcOoUyUySyaQTtbBRWBYsREI2OTsbmEkuSa4gyy3st7fzBXwu9TUkM3cWLjjFkPzfyTn/+Q+HF/zw6tC+l+33CG7xoOH9h9t3N6/f3JpPH2/eAgFvqwX9CKfgQfNO9XLsCZyiXRA01JTMnOJk1vtFNy7NKYMGIBA8xhpqwAL6syA9YV8ILGUy9dsRQTMCOaWl9R6GbuAEaliwVLscQTPJRia6YaQEiku51Us5MqXYqAYpCNzbWMyC3s7NYrV5wmpas05IzinlBFZqZvyKc5N34eL/99OPPz+//336BTu4eO9HJaW6Ek2pNtf/I4wetFSCKsquyV3KHnODdFRCEAjFzGF6qOjX9aeTzX731xMoR3Sh7bOm51Jc96EEbHQPKZs9rr6N4sNzMoLSTnCqzuuEcsxot3fb0YR4n7amLsWaw92phhRbbFO2HkHT83Otx+K22mrDbJqLxs//AgAA//+u9673";

        // 发送弹幕数据包 带牌子
//        String demoBase64Data = "AAABGwAQAAIAAAAFAAAAAHjaTE8xSzNBFLyv+Hp/w9SveO92993udRLBKjZitR4hRCNCcld4ViFFGgkYaxuxs1Ksbfw1evFvyB5o3IFhd5h98ybL/p1me1k6/xMtMJmfocTB/tHwZDQ8PgThsp42KGNkEsodiRbGW/YkKkGcFWdZi99XrsQEHftpHmQK4h5ARfh8u+3u1t3TOyja3BsNhtA2zWjW1Bejejw/T3ZJYOZ0S99i7gjd5n77uPraPIPQ3bxsH9Yfq9c/omHPYgsywat6QhqknoN37kdT75KJTF9DrWW2FUUnKVMLl1vryBaixBVFoA9Pu9fXsxkt0F6h3FUmTFqUCGYwUDEFljtrT1wtvwMAAP//TOpeDg==";

        // 发送弹幕数据包 无牌子
//        String demoBase64Data = "AAAA3AAQAAIAAAAFAAAAAHjaRM6xSgNBEAbgS2HvM/z1X8zszu7eXRcQrWIjVushel5AiYnopQrphVT2eT7zGrIHkhn4meIbZqpqNqsuq1IXJXbo31/Q4mp+u7h/XNzdgHhdLzdocxYqXaDGlJLTMmhjFmKtFo1Ba3NNSJ5C9MPy6XmQBMrUQEf8fv+cjofT8QBmJ3UU5414+xw3H/9ORaScKT53zBoojCZxog9bET+EYkDtmIFJls31drXiDuMX2vNfRD+ihVkj137usT/TKaTb/wUAAP//Q7w95A==";

        byte[] demoData = Base64.decodeBase64(demoBase64Data);

        PacketResolve resolver = new PacketResolve(demoData);

        List<Packet> packetList = resolver.getPacketList();

        for (Packet packet : packetList) {
//            System.out.println(packet);
            Message message = new PayloadResolver(packet.getPayload(), packet.getOperation()).resolve();

            switch (message.getBodyCommand()) {
                case DANMU_MSG:
                case INTERACT_WORD:
                case SEND_GIFT:
                    System.out.println(message);
                    break;
                case UNKNOWN:
                    break;
                default:
            }
        }
    }
}
