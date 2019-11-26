package space.shouly.algorithm.consistent.hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author liangbing
 * @version v1.0
 * @date 2019/11/25 11:35 上午
 **/
public class ConsistentHash<T> {

    /**
     * hash 函数
     */
    private HashAlgorithm hashAlgorithm;
    /**
     * 每个机器节点关联的虚拟节点数量
     */
    private final int numberOfReplicas;

    /**
     * 环形数据结构
     */
    private final SortedMap<Long, T> circle = new TreeMap<>();

    /**
     * 构造hash环
     *
     * @param hashAlgorithm
     * @param numberOfReplicas
     * @param nodes
     */
    public ConsistentHash(HashAlgorithm hashAlgorithm, int numberOfReplicas, Collection<T> nodes) {
        this.hashAlgorithm = hashAlgorithm;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     *
     * @param node T
     */
    public void add(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.put(this.hashAlgorithm.hash(node.toString() + i), node);
        }
    }

    /**
     * 删除真实机器节点
     *
     * @param node T
     */
    public void remove(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.remove(this.hashAlgorithm.hash(node.toString() + i));
        }
    }

    /**
     * 查询节点
     *
     * @param key
     * @return
     */
    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }

        long hash = hashAlgorithm.hash(key);

        // 沿环的顺时针找到一个虚拟节点
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

}
