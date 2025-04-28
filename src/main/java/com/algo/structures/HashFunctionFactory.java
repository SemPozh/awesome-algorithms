package com.algo.structures;

import java.util.List;
import java.util.Random;

public interface HashFunctionFactory<T> {
    List<HashFunction<T>> buildHashFunction(int k, Random random);
}
