package com.algo.structure;

import com.algo.structure.hash.function.interfaces.IntHashFunction;
import com.algo.structure.hash.function.interfaces.ObjectHashFunction;
import com.algo.structure.hash.function.interfaces.StringHashFunction;
import java.util.BitSet;
import java.util.List;

public class BloomFilter {
    private final BitSet bitSet;
    private final List<IntHashFunction> intHashFunctions;
    private final List<StringHashFunction> stringHashFunctions;
    private final List<ObjectHashFunction> objectHashFunctions;

    public BloomFilter(BloomFilterConfig config) {
        this.bitSet = new BitSet(config.getBitSetSize());
        this.intHashFunctions = config.getIntHashFunctions();
        this.stringHashFunctions = config.getStringHashFunctions();
        this.objectHashFunctions = config.getObjectHashFunctions();
    }

    private int calculateBitSetIndex(int hash) {
        return Math.abs(hash % bitSet.size());
    }

    public void putInt(int element) {
        for (IntHashFunction function : intHashFunctions) {
            bitSet.set(calculateBitSetIndex(function.hash(element)));
        }
    }

    public void putString(String element) {
        for (StringHashFunction function : stringHashFunctions) {
            bitSet.set(calculateBitSetIndex(function.hash(element)));
        }
    }

    public void putObject(Object element) {
        for (ObjectHashFunction function : objectHashFunctions) {
            bitSet.set(calculateBitSetIndex(function.hash(element)));
        }
    }

    public boolean mightContainInt(int element) {
        for (IntHashFunction function : intHashFunctions) {
            if (!bitSet.get(calculateBitSetIndex(function.hash(element)))) {
                return false;
            }
        }
        return true;
    }

    public boolean mightContainString(String element) {
        for (StringHashFunction function : stringHashFunctions) {
            if (!bitSet.get(calculateBitSetIndex(function.hash(element)))) {
                return false;
            }
        }
        return true;
    }

    public boolean mightContainObject(Object element) {
        for (ObjectHashFunction function : objectHashFunctions) {
            if (!bitSet.get(calculateBitSetIndex(function.hash(element)))) {
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
