package com.algo.structure.hash.factory;

import com.algo.structure.hash.factory.interfaces.HashFunctionFactory;
import com.algo.structure.hash.factory.interfaces.IntHashFunctionFactory;
import com.algo.structure.hash.function.interfaces.IntHashFunction;
import com.algo.structure.hash.function.IntHashFunctionImpl;

import java.util.Random;

public class IntHashFunctionFactoryImpl implements IntHashFunctionFactory {
    private final Random random;

    public IntHashFunctionFactoryImpl() {
        this.random = new Random();
    }

    public IntHashFunctionFactoryImpl(long seed) {
        this.random = new Random(seed);
    }


    @Override
    public IntHashFunction buildHashFunction() {
        return new IntHashFunctionImpl(random.nextInt());
    }
}
