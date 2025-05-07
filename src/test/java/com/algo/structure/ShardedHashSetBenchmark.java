package com.algo.structure;

import com.algo.structure.set.ShardedHashSet;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
public class ShardedHashSetBenchmark {
    @Param({"100", "1000", "10000", "100000", "1000000"})
    private int elementsCount;
    private List<String> testData;
    private ShardedHashSet<String> shardedHashSet;
    private final int partitionsCount = 10;
    private double falsePositiveProbability = 0.01;
    private Set<String> hashSet;

    @Setup(Level.Iteration)
    public void generateTestData() {
        testData = new ArrayList<>();

        for (int i = 0; i < elementsCount; i++) {
            testData.add(new Object().toString());
        }
        shardedHashSet = new ShardedHashSet<>(partitionsCount,
                elementsCount/partitionsCount,
                falsePositiveProbability);

        hashSet = new HashSet<>();
    }

    @Benchmark
    public void testShardedHashSetPerformance() {
        for (int i = 0; i < elementsCount; i++) {
            shardedHashSet.add(testData.get(i));
        }
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            shardedHashSet.contains(testData.get(random.nextInt(0, elementsCount-1)));
        }
    }

    @Benchmark
    public void testHashSetPerformance() {
        for (int i = 0; i < elementsCount; i++) {
            hashSet.add(testData.get(i));
        }
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            hashSet.contains(testData.get(random.nextInt(0, elementsCount-1)));
        }
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ShardedHashSetBenchmark.class.getSimpleName())
                .forks(1)
                .result("sharded_hash_set.csv")
                .param("expectedElementsCount", "100", "1000", "10000", "100000", "1000000")
                .build();

        new Runner(opt).run();
    }
}
