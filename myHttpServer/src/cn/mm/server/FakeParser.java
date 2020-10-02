package cn.mm.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class FakeParser extends DefaultHandler{
    private String beginTag;
    private boolean isMap;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName!=null){
            beginTag = qName;
            System.out.print("start: " + qName);
            System.out.println("     beginTag : " + beginTag);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        //解析到文本的时候，beginTag已经变成了servlet-name或者servlet-class或者url-pattern
        if(beginTag != null){
            String text = new String(ch, start, length);
            System.out.print("text: " + text);
            System.out.println("     beginTag: " + beginTag);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName != null){
            System.out.print("end: " + qName);
        }
        //每次一对标签内容解析结束，将beginTag置空
        System.out.println("     beginTag : " + beginTag);
        beginTag = null;
    }
}
