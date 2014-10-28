package com.tibidat.wordcount;

import org.xml.sax.SAXException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Pages implements Iterable<Page>, Iterator<Page> {

    private final XMLStreamReader xmlr;
    private int pageCount = 0;
    private int lim;

    public Pages(int lim, String inputFile) throws IOException, SAXException, XMLStreamException {
        this.lim = lim;
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        xmlr = xmlif.createXMLStreamReader(new FileReader(inputFile));
    }

    @Override
    public Iterator<Page> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        try {
            return pageCount < lim && xmlr.hasNext();
        } catch (XMLStreamException e) {
            return false;
        }
    }

    @Override
    public Page next() {
        boolean insidePage = false;
        boolean title = false;
        boolean text = false;
        Page page = new Page();
        try {
            pageCount++;

            loop:
            while (true) {
                xmlr.next();
                switch (xmlr.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (xmlr.getLocalName().equals("page")) {
                            insidePage = true;
                        }
                        if (xmlr.getLocalName().equals("title")) {
                            title = true;
                        }
                        if (xmlr.getLocalName().equals("text")) {
                            text = true;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if (xmlr.getLocalName().equals("page") && !insidePage) {
                            break loop;
                        }
                        if (xmlr.getLocalName().equals("title")) {
                            title = false;
                        }
                        if (xmlr.getLocalName().equals("text")) {
                            text = false;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (insidePage) {
                            int start = xmlr.getTextStart();
                            int length = xmlr.getTextLength();
                            String str = new String(xmlr.getTextCharacters(),
                                    start,
                                    length);
                            if (title) {
                                if (str.contains(":")) {
                                    insidePage = false;
                                    break;
                                } else {
                                    page.setTitle(str);
                                }
                            }
                            if (text) {
                                page.setText(str);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return page;
    }
}
