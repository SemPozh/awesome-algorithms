package com.algo.structure.hash.factory;

import com.algo.structure.hash.HashFunction;
import com.algo.structure.hash.HashFunctionFactory;
import com.algo.structure.hash.function.IntHashFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntHashFunctionFactory implements HashFunctionFactory<Integer> {
    @Override
    public List<HashFunction<Integer>> buildHashFunctions(int hashFunctionsCount, Random random) {
        List<HashFunction<Integer>> functions = new ArrayList<>();
        for (int i = 0; i < hashFunctionsCount; i++) {
            functions.add(new IntHashFunction(random.nextInt()));
        }
        return functions;
    }
}
