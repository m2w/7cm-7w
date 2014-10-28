package com.tibidat.wordcount;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Counter implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<Page> queue;
    private Map<String, Integer> counts;

    public Counter(BlockingQueue<Page> queue, Map<String, Integer> counts) {
        this.queue = queue;
        this.counts = counts;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Page page = queue.take();
                if (page.isPoisonPill()) {
                    break;
                }
                Iterable<String> words = new Words(page.getText());
                for (String word : words) {
                    countWord(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void countWord(String word) {
        Counter.lock.lock();
        try {
            Integer currentCount = counts.get(word);
            if (currentCount == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, currentCount + 1);
            }
        } finally {
            Counter.lock.unlock();
        }
    }
}
