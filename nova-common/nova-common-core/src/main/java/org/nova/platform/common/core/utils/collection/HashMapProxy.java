package org.nova.platform.common.core.utils.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 链式map
 *
 * @author 张岳
 * @date 2021/2/26 14:47
 */
public class HashMapProxy<K, V> extends HashMap<K, V> {

    /**
     * Description: 构造函数
     *
     * @param initialCapacity initialCapacity
     * @auther 张岳
     * @date 2021/2/26 14:47
     */
    public HashMapProxy(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Description: 构造函数
     *
     * @auther 张岳
     * @date 2021/2/26 14:47
     */
    public HashMapProxy() {
        super();
    }

    /**
     * Description: 构造函数
     *
     * @param m m
     * @auther 张岳
     * @date 2021/2/26 14:47
     */
    public HashMapProxy(Map<? extends K, ? extends V> m) {
        super(m);
    }

    /**
     * Description: 构造函数
     *
     * @param initialCapacity initialCapacity
     * @param loadFactor      loadFactor
     * @auther 张岳
     * @date 2021/2/26 14:47
     */
    public HashMapProxy(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Description: 对 HashMap 的 put() 的方法进行封转返回  HashMapProxy 来实现 链式添加
     *
     * @param key   key
     * @param value value
     */
    public HashMapProxy putObject(K key, V value) {
        this.put(key, value);
        return this;
    }

    /**
     * 对 HashMap 的 remove() 的方法进行封转返回  HashMapProxy 来实现 链式添加
     *
     * @param key
     * @return
     */
    public HashMapProxy removeObject(K key) {
        this.remove(key);
        return this;
    }

}