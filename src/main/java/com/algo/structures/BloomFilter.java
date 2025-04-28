package com.algo.structures;

import com.algo.structures.hash.HashFunction;
import com.algo.structures.hash.factory.IntHashFunctionFactory;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BloomFilter {
    private final BitSet bitSet;
    private final int size;
    private final List<HashFunction<Integer>> intHashFunctions;

    public BloomFilter(int size,
                       IntHashFunctionFactory intFactory,
                       int k,
                       Random random) {
        this.bitSet = new BitSet(size);
        this.size = size;
        this.intHashFunctions = intFactory.buildHashFunctions(k, random);
    }

    public void putInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            int hash = f.hash(element);
            bitSet.set(Math.abs(hash % size));
        }
    }

    public boolean mightContainInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            int hash = f.hash(element);
            if (!bitSet.get(Math.abs(hash % size))) return false;
        }
        return true;
    }

    public double estimateElementCount() {
        int bitCount = bitSet.cardinality();
        double ratio = (double) bitCount / size;
        if (ratio == 0) return 0;
        int k = intHashFunctions.size(); // Предполагаем, что k одинаково для всех типов
        return -size * Math.log(1 - ratio) / k;
    }
}
