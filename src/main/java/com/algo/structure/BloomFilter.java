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
    private final List<HashFunction<Integer>> intHashFunctions;
    private final List<HashFunction<String>> stringHashFunctions;
    private final List<HashFunction<Object>> objectHashFunctions;

    public BloomFilter(int size,
                       IntHashFunctionFactory intFactory,
                       StringHashFunctionFactory stringFactory,
                       ObjectHashFunctionFactory objectFactory,
                       int hashFunctionsCount,
                       Random random) {
        this.bitSet = new BitSet(size);
        this.intHashFunctions = intFactory.buildHashFunctions(hashFunctionsCount, random);
        this.stringHashFunctions = stringFactory.buildHashFunctions(hashFunctionsCount, random);
        this.objectHashFunctions = objectFactory.buildHashFunctions(hashFunctionsCount, random);
    }

    private int calculateBitSetIndex(int hash) {
        return Math.abs(hash % bitSet.size());
    }

    public void putInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            bitSet.set(calculateBitSetIndex(f.hash(element)));
        }
    }

    public void putString(String element) {
        for (HashFunction<String> f : stringHashFunctions) {
            bitSet.set(calculateBitSetIndex(f.hash(element)));
        }
    }

    public void putObject(Object element) {
        for (HashFunction<Object> f : objectHashFunctions) {
            bitSet.set(calculateBitSetIndex(f.hash(element)));
        }
    }

    public boolean mightContainInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            if (!bitSet.get(calculateBitSetIndex(f.hash(element)))) {
                return false;
            }
        }
        return true;
    }

    public boolean mightContainString(String element) {
        for (HashFunction<String> f : stringHashFunctions) {
            if (!bitSet.get(calculateBitSetIndex(f.hash(element)))) {
                return false;
            }
        }
        return true;
    }

    public boolean mightContainObject(Object element) {
        for (HashFunction<Object> f : objectHashFunctions) {
            if (!bitSet.get(calculateBitSetIndex(f.hash(element)))) {
                return false;
            }
        }
        return true;
    }

    public double estimatedCardinality() {
        int bitCount = bitSet.cardinality();
        if (bitCount == 0) {
            return 0;
        }
        double ratio = (double) bitCount / bitSet.size();
        return -bitSet.size() * Math.log(1 - ratio) / intHashFunctions.size();
    }
}
