package com.algo.structures.hash.factory;

import com.algo.structures.HashFunction;
import com.algo.structures.HashFunctionFactory;
import com.algo.structures.hash.function.IntHashFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntHashFunctionFactory implements HashFunctionFactory<Integer> {
    @Override
    public List<HashFunction<Integer>> buildHashFunction(int k, Random random) {
        List<HashFunction<Integer>> functions = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            functions.add(new IntHashFunction(random.nextInt()));
        }
        return functions;
    }
}

