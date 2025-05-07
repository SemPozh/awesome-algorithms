package com.algo.structure.hash.function;

import com.algo.structure.hash.function.interfaces.StringHashFunction;

public class StringHashFunctionImpl implements StringHashFunction {
    private final int seed;

    public StringHashFunctionImpl(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(String element) {
        return element.hashCode() ^ seed;
    }
}
