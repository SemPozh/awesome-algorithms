package com.algo.structure.bloom;

import com.algo.structure.hash.factory.IntHashFunctionFactoryImpl;
import com.algo.structure.hash.factory.ObjectHashFunctionFactoryImpl;
import com.algo.structure.hash.factory.StringHashFunctionFactoryImpl;
import com.algo.structure.hash.factory.interfaces.IntHashFunctionFactory;
import com.algo.structure.hash.factory.interfaces.ObjectHashFunctionFactory;
import com.algo.structure.hash.factory.interfaces.StringHashFunctionFactory;
import com.algo.structure.hash.function.interfaces.IntHashFunction;
import com.algo.structure.hash.function.interfaces.ObjectHashFunction;
import com.algo.structure.hash.function.interfaces.StringHashFunction;
import java.util.ArrayList;
import java.util.List;

public class BloomFilterConfig {
    private final int bitSetSize;
    private final double falsePositiveProbability;
    private final int expectedElementsCount;
    private final int hashFunctionsCount;
    private final Long seed;

    private List<IntHashFunction> intHashFunctions;
    private List<StringHashFunction> stringHashFunctions;
    private List<ObjectHashFunction> objectHashFunctions;
    private final IntHashFunctionFactory intHashFunctionFactory;
    private final StringHashFunctionFactory stringHashFunctionFactory;
    private final ObjectHashFunctionFactory objectHashFunctionFactory;



    private BloomFilterConfig(Builder builder) {
        this.expectedElementsCount = builder.expectedElementsCount;
        this.falsePositiveProbability = builder.falsePositiveProbability;
        this.bitSetSize = calculateOptimalBitSetSize();
        this.hashFunctionsCount = calculateOptimalHashFunctionsCount();
        this.intHashFunctions = builder.intHashFunctions;
        this.stringHashFunctions = builder.stringHashFunctions;
        this.objectHashFunctions = builder.objectHashFunctions;
        this.seed = builder.seed;

        if (this.seed != null) {
            this.intHashFunctionFactory = new IntHashFunctionFactoryImpl(seed);
            this.stringHashFunctionFactory = new StringHashFunctionFactoryImpl(seed);
            this.objectHashFunctionFactory = new ObjectHashFunctionFactoryImpl(seed);
        } else {
            this.intHashFunctionFactory = new IntHashFunctionFactoryImpl();
            this.stringHashFunctionFactory = new StringHashFunctionFactoryImpl();
            this.objectHashFunctionFactory = new ObjectHashFunctionFactoryImpl();
        }

        this.objectHashFunctions = new ArrayList<>();
        this.intHashFunctions = new ArrayList<>();
        this.stringHashFunctions = new ArrayList<>();
        initHashFunctions();
    }

    private void initHashFunctions() {
        for (int i = 0; i < hashFunctionsCount; i++) {
            objectHashFunctions.add(objectHashFunctionFactory.buildHashFunction());
            intHashFunctions.add(intHashFunctionFactory.buildHashFunction());
            stringHashFunctions.add(stringHashFunctionFactory.buildHashFunction());
        }
    }

    private int calculateOptimalBitSetSize() {
        return (int) Math.ceil(-expectedElementsCount * Math.log(falsePositiveProbability)
                / Math.pow(Math.log(2), 2));
    }

    private int calculateOptimalHashFunctionsCount() {
        return Math.max(1, (int) Math.round(((double) bitSetSize / expectedElementsCount)
                * Math.log(2)));
    }

    public List<IntHashFunction> getIntHashFunctions() {
        return intHashFunctions;
    }

    public List<StringHashFunction> getStringHashFunctions() {
        return stringHashFunctions;
    }

    public List<ObjectHashFunction> getObjectHashFunctions() {
        return objectHashFunctions;
    }

    public int getBitSetSize() {
        return this.bitSetSize;
    }

    public static class Builder {
        private int expectedElementsCount = 100;
        private double falsePositiveProbability = 0.03;
        private Long seed = null;
        private List<IntHashFunction> intHashFunctions;
        private List<StringHashFunction> stringHashFunctions;
        private List<ObjectHashFunction> objectHashFunctions;

        public Builder withFalsePositiveProbability(double probability) {
            if (probability <= 0 || probability >= 1) {
                throw new IllegalArgumentException("The probability of error must be in (0, 1)");
            }
            this.falsePositiveProbability = probability;
            return this;
        }

        public Builder withExpectedElementsCount(int expectedElementsCount) {
            this.expectedElementsCount = expectedElementsCount;
            return this;
        }

        public Builder withSeed(long seed) {
            this.seed = seed;
            return this;
        }

        public BloomFilterConfig build() {
            return new BloomFilterConfig(this);
        }
    }
}
