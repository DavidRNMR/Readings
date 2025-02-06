package com.readings.adapters.xml;

import com.readings.domain.Reading;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlReadingAdapter {

    public List<Reading> readFromXml (String filePath) throws Exception{

        List<Reading> readings = new ArrayList<>();

        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new File(filePath));

        NodeList readingNodes = document.getElementsByTagName("reading");

        for (int i = 0; i< readingNodes.getLength(); i++){

            Node node = readingNodes.item(i);

            Element element = (Element) node;

            String clientId = element.getAttribute("clientID");
            String period = element.getAttribute("period");

            String textValue = element.getTextContent().trim();
            double value = Double.parseDouble(textValue);

            readings.add(new Reading(clientId,period,value));
        }
        return readings;
    }
}
