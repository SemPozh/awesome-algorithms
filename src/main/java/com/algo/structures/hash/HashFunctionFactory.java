package com.algo.structures.hash;

import java.util.List;
import java.util.Random;

public interface HashFunctionFactory<T> {
    List<HashFunction<T>> buildHashFunctions(int k, Random random);
}
