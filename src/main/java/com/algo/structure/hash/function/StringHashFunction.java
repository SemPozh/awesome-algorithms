package com.algo.structure.hash.function;

import com.algo.structure.hash.HashFunction;

public class StringHashFunction implements HashFunction<String> {
    private final int seed;

    public StringHashFunction(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(String element) {
        return element.hashCode() ^ seed;
    }
}
