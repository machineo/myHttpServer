package cn.mm.test;

import cn.mm.server.FakeParser;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XMLHandlerTest {
    @Test
    public void fun1() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        FakeParser fp = new FakeParser();
        saxParser.parse("src/web.xml", fp);

    }
}
