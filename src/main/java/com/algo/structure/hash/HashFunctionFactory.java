package com.algo.structure.hash;

import java.util.List;
import java.util.Random;

public interface HashFunctionFactory<T> {
    List<HashFunction<T>> buildHashFunctions(int hashFunctionsCount, Random random);
}
