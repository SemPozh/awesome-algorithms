package com.algo.structure.hash.factory;

import com.algo.structure.hash.factory.interfaces.StringHashFunctionFactory;
import com.algo.structure.hash.function.interfaces.HashFunction;
import com.algo.structure.hash.factory.interfaces.HashFunctionFactory;
import com.algo.structure.hash.function.StringHashFunctionImpl;
import com.algo.structure.hash.function.interfaces.StringHashFunction;

import java.util.Random;

public class StringHashFunctionFactoryImpl implements StringHashFunctionFactory {
    private final Random random;

    public StringHashFunctionFactoryImpl() {
        this.random = new Random();
    }

    public StringHashFunctionFactoryImpl(long seed) {
        this.random = new Random(seed);
    }


    @Override
    public StringHashFunction buildHashFunction() {
        return new StringHashFunctionImpl(random.nextInt());
    }
}
