package com.tibidat.wordcount;

import java.util.Arrays;
import java.util.Iterator;

public class Words implements Iterable<String> {
    private final String text;

    public Words(String text) {
        this.text = text;
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.asList(text.split(" ")).iterator();
    }
}
