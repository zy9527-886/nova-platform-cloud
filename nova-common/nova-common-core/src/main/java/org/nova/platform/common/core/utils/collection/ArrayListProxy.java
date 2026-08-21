package org.nova.platform.common.core.utils.collection;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Description: 链式List
 *
 * @author 张岳
 * @date 2021/2/26 14:49
 */
public class ArrayListProxy<E> extends ArrayList<E> {

    /**
     * Description: 构造函数
     *
     * @param c c
     * @auther 张岳
     * @date 2021/2/26 14:49
     */
    public ArrayListProxy(Collection<? extends E> c) {
        super(c);
    }

    /**
     * Description:构造函数
     *
     * @param initialCapacity initialCapacity
     * @auther 张岳
     * @date 2021/2/26 14:49
     */
    public ArrayListProxy(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Description:构造函数
     *
     * @auther 张岳
     * @date 2021/2/26 14:49
     */
    public ArrayListProxy() {
        super();
    }

    /**
     * Description: 对 ArrayList 的 add() 的方法进行封转返回  ArrayListProxy 来实现 链式添加
     *
     * @param e e
     */
    public ArrayListProxy addObject(E e) {
        if (this.add(e)) {
            return this;
        }
        throw new ArrayStoreException("ArrayListProxy add element fail!");
    }

}