package com.zwrss.collections.mutable;

public class HashMap<K, V> {

    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int bucketsCount = 16;
    private LinkedList[] buckets = new LinkedList[bucketsCount];

    public void put(K key, V value) {
        int bucketNo = key.hashCode() % bucketsCount;
        if (buckets[bucketNo] == null) buckets[bucketNo] = new LinkedList<Entry>();
        buckets[bucketNo].add(new Entry(key, value));
    }

    public V get(K key) {
        LinkedList<Entry> bucket = buckets[key.hashCode() % bucketsCount];
        if (bucket == null) return null;
        Entry entry = bucket.find(e -> e.key.equals(key));
        return entry == null ? null : entry.value;
    }

}
