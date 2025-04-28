package com.algo.structure.hash.function;

import com.algo.structure.hash.HashFunction;

public class IntHashFunction implements HashFunction<Integer> {
    private final int seed;

    public IntHashFunction(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(Integer element) {
        return (element ^ seed) * 0x9e3775cd;
    }
}
