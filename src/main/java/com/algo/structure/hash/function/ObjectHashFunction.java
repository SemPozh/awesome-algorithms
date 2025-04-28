package com.algo.structure.hash.function;

import com.algo.structure.hash.HashFunction;

public class ObjectHashFunction implements HashFunction<Object> {
    private final int seed;

    public ObjectHashFunction(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(Object element) {
        return (element.hashCode() ^ seed) * 0x9e3775cd;
    }
}
