package com.algo.structure.hash.function;

import com.algo.structure.hash.function.interfaces.IntHashFunction;

public class IntHashFunctionImpl implements IntHashFunction {
    private final int seed;

    public IntHashFunctionImpl(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(int value) {
        return Integer.hashCode(value) ^ seed;
    }
}
