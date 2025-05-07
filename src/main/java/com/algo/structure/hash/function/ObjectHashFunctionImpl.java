package com.algo.structure.hash.function;

import com.algo.structure.hash.function.interfaces.ObjectHashFunction;

public class ObjectHashFunctionImpl implements ObjectHashFunction {
    private final int seed;

    public ObjectHashFunctionImpl(int seed) {
        this.seed = seed;
    }

    @Override
    public int hash(Object element) {
        return element.hashCode() ^ seed;
    }
}
