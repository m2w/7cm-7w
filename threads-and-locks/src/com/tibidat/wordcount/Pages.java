package com.tibidat.wordcount;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// TODO: check if XMLStreamReader is a better solution than SAX
public class Pages implements Iterable<Page> {
    private ArrayList<Page> pages = new ArrayList<Page>();

    public Pages(int limit, String inputFile) throws IOException, SAXException {
        final int lim = limit;
        XMLReader xr = XMLReaderFactory.createXMLReader();
        DefaultHandler handler = new DefaultHandler() {
            private boolean readingPage = false;
            private boolean readingTitle = false;
            private boolean readingText = false;
            private int pageCount = 0;

            public void startElement(String uri, String name, String qName, Attributes stats) {
                if (qName.equals("page")) {
                    pageCount++;
                    pages.add(new Page());
                    readingPage = true;
                }
                if (qName.equals("title") && readingPage) {
                    readingTitle = true;
                }
                if (qName.equals("text") && readingPage) {
                    readingText = true;
                }
            }

            public void endElement(String uri, String name, String qName) throws LimitReachedException {
                if (qName.equals("page")) {
                    readingPage = false;
                    readingTitle = false;
                    readingText = false;
                    if (pageCount >= lim) {
                        throw new LimitReachedException();
                    }
                }
            }

            public void characters(char ch[], int start, int end) {
                String str = new String(ch, start, end);
                if (readingTitle) {
                    pages.get(pageCount).setTitle(str);
                }
                if (readingText) {
                    pages.get(pageCount).setText(str);
                }
            }
        };
        xr.parse(new InputSource(new FileReader(inputFile)));
        xr.setContentHandler(handler);
    }

    @Override
    public Iterator<Page> iterator() {
        return pages.iterator();
    }

    private class LimitReachedException extends SAXException {
        public LimitReachedException() {
            super();
        }
    }
}
