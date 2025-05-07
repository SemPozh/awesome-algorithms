package com.algo.structure.hash.factory;

import com.algo.structure.hash.factory.interfaces.ObjectHashFunctionFactory;
import com.algo.structure.hash.function.interfaces.HashFunction;
import com.algo.structure.hash.factory.interfaces.HashFunctionFactory;
import com.algo.structure.hash.function.ObjectHashFunctionImpl;
import com.algo.structure.hash.function.interfaces.ObjectHashFunction;

import java.util.Random;

public class ObjectHashFunctionFactoryImpl implements ObjectHashFunctionFactory {
    private final Random random;

    public ObjectHashFunctionFactoryImpl() {
        this.random = new Random();
    }

    public ObjectHashFunctionFactoryImpl(long seed) {
        this.random = new Random(seed);
    }



    @Override
    public ObjectHashFunction buildHashFunction() {
        return new ObjectHashFunctionImpl(random.nextInt());
    }

}
