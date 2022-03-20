package HashMap;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * @author 张梓毅
 */
public class MyHashMap<K,V> {
    private int size = 0;
    private int capacity;
    private float loadFactor;
    private Node<K,V>[] table;

    public MyHashMap() {
        this.capacity = 16;
        this.loadFactor = 0.75f;
        table = (Node<K, V>[]) Array.newInstance(Node.class,capacity);
    }

    public MyHashMap(int initialCapacity,float loadFactor) {
        //自定义容量不能小于0
        if (initialCapacity < 0)
            throw new IllegalArgumentException("不合法容量: " +
                    capacity);
        //自定义负载因子不能小于等于0并且不能是NaN值
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("不合法负载因子: " +
                    loadFactor);
        //自定义容量向上取2的n次方
        this.capacity = tableSizeFor(initialCapacity);
        this.loadFactor = loadFactor;
        table = (Node<K, V>[]) Array.newInstance(Node.class,capacity);
    }

    //计算Hash值
    int Hash(K key) {
        return Objects.hashCode(key) & (capacity - 1);
    }


    void put(K key,V value){
        int hash = Hash(key);
        //判断是否发生覆盖
        boolean flag = true;
        Node<K,V> node = new Node<>(key,value,hash);
        if(this.table[hash] == null){
            this.table[hash] = node;
        }else{
            Node<K,V> head = this.table[hash];
            //发生hash冲突，搜索链表
            while (head != null){
                //如果Key相等，覆盖Value
                if(key == head.key){
                    head.value = value;
                    flag = false;
                    break;
                }
                //如果hash冲突没有发生覆盖，使用尾插法，让最后一个结点的next指向新结点
//                if(head.next == null){
//                    head.next = node;
//                    break;
//                }
                head = head.next;
            }
            //如果hash冲突没有发生覆盖，使用头插法，插到第一个结点之前
            if(flag){
                head = this.table[hash];
                this.table[hash] = node;
                node.next = head;
            }
        }
        if(flag) this.size++;
        //如果元素数量超过负载因子*容量，扩容
        if(size > capacity * loadFactor) resize();
    }

    V get(K key){
        V value = null;
        int hash = Hash(key);
        if(this.table[hash] == null){
            return value;
        }else{
            Node<K,V> head = this.table[hash];
            while (head != null){
                if(head.key == key){
                    value = head.value;
                    break;
                }
                head = head.next;
            }
            return value;
        }
    }

    void resize(){
        //老的table
        Node<K,V>[] oldTable = this.table;
        //新的table，容量2倍
        this.table = (Node<K, V>[]) Array.newInstance(Node.class,2 * capacity);
        this.capacity *= 2;
        //遍历老table，把键值对都put到新table中
        for (int i = 0;i < oldTable.length; i++){
            if(oldTable[i] != null){
                Node<K,V> head = oldTable[i];
                while (head != null) {
                    put(head.key,head.value);
                    head = head.next;
                }
            }
        }
    }

    //向上取2的n次方
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

}
class Node<K,V>{
    K key;
    V value;
    int hash;
    Node<K,V> next;

    public Node(K key, V value, int hash) {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }
}