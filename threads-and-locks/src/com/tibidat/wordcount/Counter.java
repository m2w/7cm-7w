package com.tibidat.wordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Counter implements Runnable {
    private BlockingQueue<Page> queue;
    private Map<String, Integer> counts;
    private HashMap<String, Integer> localCounts = new HashMap<String, Integer>();

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
                    this.mergeCounts();
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

    private void mergeCounts() {
        for (Map.Entry<String, Integer> e : localCounts.entrySet()) {
            String word = e.getKey();
            Integer count = e.getValue();
            while (true) {
                Integer currentCount = counts.get(word);
                if (currentCount == null) {
                    if (counts.putIfAbsent(word, count) == null) {
                        break;
                    }
                } else if (counts.replace(word, currentCount, currentCount + count)) {
                    break;
                }
            }
        }
    }

    private void countWord(String word) {
        Integer currentCount = localCounts.get(word);
        if (currentCount == null) {
            localCounts.put(word, 1);
        } else {
            localCounts.put(word, currentCount + 1);
        }
    }
}
