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

    public void putInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            int hash = f.hash(element);
            bitSet.set(Math.abs(hash % bitSet.size()));
        }
    }

    public void putString(String element) {
        for (HashFunction<String> f : stringHashFunctions) {
            int hash = f.hash(element);
            bitSet.set(Math.abs(hash % bitSet.size()));
        }
    }

    public void putObject(Object element) {
        for (HashFunction<Object> f : objectHashFunctions) {
            int hash = f.hash(element);
            bitSet.set(Math.abs(hash % bitSet.size()));
        }
    }

    public boolean mightContainInt(int element) {
        for (HashFunction<Integer> f : intHashFunctions) {
            int hash = f.hash(element);
            if (!bitSet.get(Math.abs(hash % bitSet.size()))) {
                return false;
            }
        }
        return true;
    }

    public boolean mightContainString(String element) {
        for (HashFunction<String> f : stringHashFunctions) {
            int hash = f.hash(element);
            if (!bitSet.get(Math.abs(hash % bitSet.size()))) {
                return false;
            }
        }
        return true;
    }

    public boolean mightContainObject(Object element) {
        for (HashFunction<Object> f : objectHashFunctions) {
            int hash = f.hash(element);
            if (!bitSet.get(Math.abs(hash % bitSet.size()))) {
                return false;
            }
        }
        return true;
    }

    public double estimateElementCount() {
        int bitCount = bitSet.cardinality();
        double ratio = (double) bitCount / bitSet.size();
        if (ratio == 0) {
            return 0;
        }
        return -bitSet.size() * Math.log(1 - ratio) / intHashFunctions.size();
    }
}
