package org.redi;

import java.util.LinkedList;
import java.util.List;

public class HashMap<K, V> implements Map<K, V> {

    private final int capacity = 1_000;
    private final List<Entry<K, V>>[] buckets = new List[capacity];

    public HashMap() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    @Override
    public V put(K key, V value) {
        if (key == null) return null;
        List<Entry<K, V>> bucket = buckets[key.hashCode() % capacity];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                if (entry.value.equals(value)) {
                    return value;
                } else {
                    V prev = entry.value;
                    entry.setValue(value);
                    return prev;
                }
            }
        }

        bucket.add(new Entry<>(key, value));
        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) return null;
        List<Entry<K, V>> bucket = buckets[key.hashCode() % capacity];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) return null;
        List<Entry<K, V>> bucket = buckets[key.hashCode() % capacity];

        Entry<K, V> found = null;

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                found = entry;
                break;
            }
        }

        if (found != null) {
            bucket.remove(found);
            return found.value;
        }

        return null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) return false;
        List<Entry<K, V>> bucket = buckets[key.hashCode() % capacity];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    private static class Entry<K, V> {
        final K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (Integer i = 5_000; i < 5_010; i++) {
            System.out.println(map.put(i, i+1));
            System.out.println(map.get(i));
        }
    }

}
