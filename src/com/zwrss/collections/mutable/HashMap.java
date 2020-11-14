package com.zwrss.collections.mutable;

/**
 * This is a very simple implementation of a HashMap. It in no means should be treated as alternative to Java's native
 * HashMap. This is for educational means only.
 * @param <K> type of key
 * @param <V> type of value
 */
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
    private int doubles = 0;

    private LinkedList[] buckets = new LinkedList[bucketsCount];

    public void resize(int newBucketsCount) {
        LinkedList[] oldBuckets = buckets;
        bucketsCount = newBucketsCount;
        buckets = new LinkedList[newBucketsCount];
        doubles = 0;
        for (LinkedList<Entry> bucket : buckets) {
            if (bucket != null) bucket.foreach(entry -> put(entry.key, entry.value));
        }
    }

    public void put(K key, V value) {
        int bucketNo = key.hashCode() % bucketsCount;
        if (buckets[bucketNo] == null) buckets[bucketNo] = new LinkedList<Entry>();
        LinkedList<Entry> bucket = buckets[bucketNo];
        bucket.add(new Entry(key, value));
        if (bucket.size() == 2) doubles += 1;
        if (doubles > bucketsCount / 2) resize(bucketsCount * 2);
    }

    public V get(K key) {
        LinkedList<Entry> bucket = buckets[key.hashCode() % bucketsCount];
        if (bucket == null) return null;
        Entry entry = bucket.find(e -> e.key.equals(key));
        return entry == null ? null : entry.value;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

}
