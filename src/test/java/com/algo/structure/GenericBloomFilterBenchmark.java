package com.algo.structure;

import com.algo.structure.bloom.BloomFilter;
import com.algo.structure.bloom.BloomFilterConfig;
import com.algo.structure.bloom.GenericBloomFilter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
public class GenericBloomFilterBenchmark {
    @Param({"10", "25", "50", "100", "1000", "2000", "5000", "10000", "25000", "50000", "100000"})
    private int expectedElementsCount;
    private GenericBloomFilter<Integer> bloomFilter;
    private int[] testData;
    private int[] nonExistingData;

    @Setup(Level.Trial)
    public void initBloomFilter() {
        BloomFilterConfig config = new BloomFilterConfig.Builder()
                .withExpectedElementsCount(this.expectedElementsCount)
                .withFalsePositiveProbability(0.01)
                .build();
        bloomFilter = new GenericBloomFilter<>(new BloomFilter(config));
    }

    @Setup(Level.Iteration)
    public void generateTestData() {
        testData = new int[expectedElementsCount];
        nonExistingData = new int[expectedElementsCount];

        for (int i = 0; i < expectedElementsCount; i++) {
            testData[i] = i;
            nonExistingData[i] = expectedElementsCount+i;
        }
    }

    @Benchmark
    public void benchmarkAddOperation(Blackhole blackhole) {
        for (int item : testData) {
            bloomFilter.put(item);
        }
        blackhole.consume(bloomFilter);
    }

    @Benchmark
    public void benchmarkContainsExisting(Blackhole blackhole) {
        for (int item : testData) {
            blackhole.consume(bloomFilter.mightContain(item));
        }
    }

    @Benchmark
    public void benchmarkContainsNonExisting(Blackhole blackhole) {
        for (int item : nonExistingData) {
            blackhole.consume(bloomFilter.mightContain(item));
        }
    }

    @Benchmark
    public void benchmarkMemoryUsage(Blackhole blackhole) {
        BloomFilterConfig config = new BloomFilterConfig.Builder()
                .withExpectedElementsCount(expectedElementsCount)
                .build();

        GenericBloomFilter<Integer> tempFilter = new GenericBloomFilter<>(
                new BloomFilter(config)
        );
        for (Integer item : testData) {
            tempFilter.put(item);
        }

        long memoryUsage = GraphLayout.parseInstance(tempFilter).totalSize();
        blackhole.consume(tempFilter);
        System.out.println("Elements: " + expectedElementsCount +
                " Memory: " + memoryUsage + " bytes");
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(BloomFilterBenchmark.class.getSimpleName())
                .forks(1)
                .result("generic_bloom_filter.csv")
                .param("expectedElementsCount", "10", "25", "50", "100", "1000", "2000", "5000", "10000", "25000", "50000", "100000")
                .build();

        new Runner(opt).run();
    }
}
