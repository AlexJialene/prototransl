package com.syuio.kits;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:12
 */
public abstract class VolumeKit {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;

    public VolumeKit() {
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return new HashMap(size);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int size) {
        return new LinkedHashMap(size);
    }

    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int size) {
        return new ConcurrentHashMap(size);
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    public static <T> ArrayList<T> newArrayList(int size) {
        return new ArrayList(size);
    }

    public static <T> HashSet<T> newHashSet() {
        return new HashSet();
    }

    public static <T> HashSet<T> newHashSet(int size) {
        return new HashSet(size);
    }

    public static <T> TreeSet<T> newTreeSet() {
        return new TreeSet();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return null == map || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] arr) {
        return null == arr || arr.length == 0;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean hasItems(Enumeration<?> enums) {
        return enums != null && enums.hasMoreElements();
    }

    public static boolean hasNotItems(Enumeration<?> enums) {
        return enums == null || !enums.hasMoreElements();
    }

    public static boolean hasItems(Iterator<?> iters) {
        return iters != null && iters.hasNext();
    }

    public static boolean hasNotItems(Iterator<?> iters) {
        return iters != null && iters.hasNext();
    }

    public static <E> ArrayList<E> createArrayList() {
        return new ArrayList();
    }

    public static <E> ArrayList<E> createArrayList(int initialCapacity) {
        return new ArrayList(initialCapacity);
    }

    public static <E> ArrayList<E> createArrayList(Collection<? extends E> collection) {
        return collection == null ? new ArrayList() : new ArrayList(collection);
    }

    public static <T, V extends T> ArrayList<T> createArrayList(V... args) {
        if (args != null && args.length != 0) {
            ArrayList<T> list = new ArrayList(args.length);
            Object[] var2 = args;
            int var3 = args.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                V v = (V) var2[var4];
                list.add(v);
            }

            return list;
        } else {
            return new ArrayList();
        }
    }

    public static <E> LinkedList<E> createLinkedList() {
        return new LinkedList();
    }
}
