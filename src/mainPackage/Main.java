package mainPackage;

import naturalLanguageProcessor.TextProcessor;
import parser.*;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import profiles.Bug;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main
{
    public static void main(String[] args) throws Exception {

        XMLParser parser = new XMLParser();
        parser.parsing();
        //System.out.println(parser.toString());

        for(Map.Entry b: parser.getMapOfBugs().entrySet())
        {
            System.out.println(b.getValue());
        }

        System.out.println("-----------------------------------------------");

        for(Map.Entry d: parser.getMapOfDevelopers().entrySet())
        {
            System.out.println(d.getValue());
        }
    }
}
