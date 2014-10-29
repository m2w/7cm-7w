package com.tibidat.wordcount;

import java.text.BreakIterator;
import java.util.Iterator;

public class Words implements Iterable<String>, Iterator<String> {
    private final String text;
    private BreakIterator bi;
    private int start = 0;
    private int end = 0;

    public Words(String text) {
        bi = BreakIterator.getWordInstance();
        bi.setText(text);
        this.text = text;
        start = bi.first();
        end = bi.next();
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return end != BreakIterator.DONE;
    }

    @Override
    public String next() {
        String hit = text.substring(start, end);
        start = end;
        end = bi.next();
        return hit;
    }
}
