package space.shouly.algorithm.consistent.hash;

/**
 * @author liangbing
 * @version v1.0
 * @date 2019/11/25 11:38 上午
 **/
public interface HashAlgorithm {

    /**
     * 生成hash
     *
     * @param key
     * @return
     */
    Long hash(String key);
}
