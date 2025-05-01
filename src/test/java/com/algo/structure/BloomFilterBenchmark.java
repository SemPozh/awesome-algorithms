package com.algo.structure;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class BloomFilterBenchmark {
    private int var1;
    private int var2;

    @Setup
    public void setup() {
        var1 = 3;
        var2 = 7;
    }

    @Benchmark
    public int benchmarkAddition() {
        return var1 + var2;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
