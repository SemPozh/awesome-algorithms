package com.algo.structure;

public class GenericBloomFilter<T> {
    private final BloomFilter bloomFilter;

    public GenericBloomFilter(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }

    public void put(T element) {
        if (element instanceof Integer) {
            bloomFilter.putInt((Integer) element);
        } else if (element instanceof String) {
            bloomFilter.putString((String) element);
        } else {
            bloomFilter.putObject(element);
        }
    }

    public boolean mightContain(T element) {
        if (element instanceof Integer) {
            return bloomFilter.mightContainInt((Integer) element);
        } else if (element instanceof String) {
            return bloomFilter.mightContainString((String) element);
        } else {
            return bloomFilter.mightContainObject(element);
        }
    }
}
