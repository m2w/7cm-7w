package com.tibidat.wordcount;

import java.util.HashMap;

public class WordCount {
    private static final HashMap<String, Integer> counts = new HashMap<String, Integer>();

    public static void main(String[] args) throws Exception {
        Iterable<Page> pages = new Pages(10000, "/Users/blah/Downloads/simplewiki-20141025-pages-meta-current.xml");
        for (Page page : pages) {
            Iterable<String> words = new Words(page.getText());
            for (String word : words) {
                countWord(word);
            }
        }
    }

    private static void countWord(String word) {
        Integer currentCount = counts.get(word);
        if (currentCount == null) {
            counts.put(word, 1);
        } else {
            counts.put(word, currentCount + 1);
        }
    }
}
