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
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return bi.next() != BreakIterator.DONE;
    }

    @Override
    public String next() {
        end = bi.current();
        String hit = text.substring(start, end);
        start = end;
        if (Character.isLetterOrDigit(hit.charAt(0))) {
            return hit;
        }
        // TODO: strip non-letters?
        return "";
    }
}
