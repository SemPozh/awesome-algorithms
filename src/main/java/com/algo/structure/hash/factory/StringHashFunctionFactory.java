package com.algo.structure.hash.factory;

import com.algo.structure.hash.HashFunction;
import com.algo.structure.hash.HashFunctionFactory;
import com.algo.structure.hash.function.StringHashFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringHashFunctionFactory implements HashFunctionFactory<String> {
    @Override
    public List<HashFunction<String>> buildHashFunctions(int k, Random random) {
        List<HashFunction<String>> functions = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            functions.add(new StringHashFunction(random.nextInt()));
        }
        return functions;
    }
}