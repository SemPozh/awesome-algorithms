package com.algo.structure;

import com.algo.structure.hash.HashFunction;
import com.algo.structure.hash.factory.IntHashFunctionFactory;
import com.algo.structure.hash.factory.ObjectHashFunctionFactory;
import com.algo.structure.hash.factory.StringHashFunctionFactory;

import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BloomFilter {
    private final BitSet bitSet;
    private final int size;
    private final List<HashFunction<Integer>> intHashFunctions;
    private final List<HashFunction<String>> stringHashFunctions;
    private final List<HashFunction<Object>> objectHashFunctions;

    public BloomFilter(int size,
                       IntHashFunctionFactory intFactory,
                       StringHashFunctionFactory stringFactory,
                       ObjectHashFunctionFactory objectFactory,
                       int k,
                       Random random) {
        this.bitSet = new BitSet(size);
        this.size = size;
        this.intHashFunctions = intFactory.buildHashFunctions(k, random);
        this.stringHashFunctions = stringFactory.buildHashFunctions(k, random);
        this.objectHashFunctions = objectFactory.buildHashFunctions(k, random);
    }

    public void putInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            int hash = f.hash(element);
            bitSet.set(Math.abs(hash % size));
        }
    }

    public void putString(String element) {
        for (HashFunction<String> f : stringHashFunctions) {
            int hash = f.hash(element);
            bitSet.set(Math.abs(hash % size));
        }
    }

    public void putObject(Object element) {
        for (HashFunction<Object> f : objectHashFunctions) {
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

    public boolean mightContainString(String element) {
        for (HashFunction<String> f : stringHashFunctions) {
            int hash = f.hash(element);
            if (!bitSet.get(Math.abs(hash % size))) return false;
        }
        return true;
    }

    public boolean mightContainObject(Object element) {
        for (HashFunction<Object> f : objectHashFunctions) {
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
