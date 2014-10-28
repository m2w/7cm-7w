package com.tibidat.wordcount;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordCount {
    private static final ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<Page>(100);
    private static final HashMap<String, Integer> counts = new HashMap<String, Integer>();
    private static final int NUM_COUNTERS = 2;

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i=0;i<NUM_COUNTERS;++i) {
            executor.execute(new Thread(new Counter(queue, counts)));
        }
        Thread parser = new Thread(new Parser(queue));
        parser.start();
        parser.join();
        for (int i=0;i<NUM_COUNTERS;++i) {
            queue.put(new PoisonPill());
        }
        executor.shutdown();
        executor.awaitTermination(10L, TimeUnit.MINUTES);
        System.out.println(counts.size() + " words");
    }
}
