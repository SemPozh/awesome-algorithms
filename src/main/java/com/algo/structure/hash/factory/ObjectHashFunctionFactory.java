package com.algo.structure.hash.factory;

import com.algo.structure.hash.HashFunction;
import com.algo.structure.hash.HashFunctionFactory;
import com.algo.structure.hash.function.ObjectHashFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectHashFunctionFactory implements HashFunctionFactory<Object> {
    @Override
    public List<HashFunction<Object>> buildHashFunctions(int k, Random random) {
        List<HashFunction<Object>> functions = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            functions.add(new ObjectHashFunction(random.nextInt()));
        }
        return functions;
    }
}
