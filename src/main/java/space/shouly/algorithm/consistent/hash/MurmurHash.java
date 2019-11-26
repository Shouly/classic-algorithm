package space.shouly.algorithm.consistent.hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * MurmurHash算法
 * <a>https://zh.wikipedia.org/wiki/Murmur%E5%93%88%E5%B8%8C</a>
 *
 * @author liangbing
 * @version v1.0
 * @date 2019/11/16 3:57 下午
 **/
public class MurmurHash implements HashAlgorithm {

    /**
     * MurMurHash算法,性能高,碰撞率低
     *
     * @param key String
     * @return Long
     */
    @Override
    public Long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;

    }
}
