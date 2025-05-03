package com.algo.structure.hash.factory.interfaces;

import com.algo.structure.hash.function.interfaces.HashFunction;

public interface HashFunctionFactory<T> {
    HashFunction<T> buildHashFunctions();
}
