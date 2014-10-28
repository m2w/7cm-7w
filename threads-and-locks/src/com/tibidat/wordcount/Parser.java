package com.tibidat.wordcount;

import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable {
    private BlockingQueue<Page> queue;

    public Parser(BlockingQueue<Page> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Iterable<Page> pages = new Pages(100000, "/Users/blah/Downloads/simplewiki-20141025-pages-meta-current.xml");
            for (Page page : pages) {
                queue.put(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
