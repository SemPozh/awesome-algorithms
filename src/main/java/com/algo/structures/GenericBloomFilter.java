package com.algo.structures;

public class GenericBloomFilter<T> {
    private final BloomFilter bloomFilter;

    public GenericBloomFilter(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }

    public void put(T element) {
        if (element instanceof Integer) {
            bloomFilter.putInt((Integer) element);
        }
    }

    public boolean mightContain(T element) {
        if (element instanceof Integer) {
            return bloomFilter.mightContainInt((Integer) element);
        }
        return false;
    }
}
