package com.algo.structure.bloom;

public class GenericBloomFilter<T> {
    private final BloomFilter bloomFilter;

    public GenericBloomFilter(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }

    public void put(T element) {
        bloomFilter.putObject(element);
    }

    public boolean mightContain(T element) {
        return bloomFilter.mightContainObject(element);
    }
}
