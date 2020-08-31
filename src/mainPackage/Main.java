package mainPackage;

import parser.*;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        XMLParser parser = new XMLParser();
        parser.parsing();
    }
}
