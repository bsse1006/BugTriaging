package parser;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import profiles.Bug;
import profiles.Developer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser
{
    Map<String, Bug> mapOfBugs = new HashMap<>();
    Map<String, Developer> mapOfDevelopers = new HashMap<>();

    private Element extractRootElement (String filepath)
    {
        File inputFile = new File(filepath);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(inputFile);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Root element :" + document.getRootElement().getName());
        Element root = document.getRootElement();

        return root;
    }

    public void bugDescriptionParsing ()
    {
        Element root = extractRootElement("C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\fixedData.xml");

        List<Element> listOfBugs = root.getChildren();

        for (int iteratorForListOfBugs = 0; iteratorForListOfBugs < listOfBugs.size(); iteratorForListOfBugs++)
        {
            Element bugElement = listOfBugs.get(iteratorForListOfBugs);

            Bug bugObject = new Bug(
                    bugElement.getChild("id").getText(),
                    bugElement.getChild("creation_time").getText(),
                    bugElement.getChild("product").getText(),
                    bugElement.getChild("component").getText(),
                    bugElement.getChild("bug_severity").getText()
            );

            mapOfBugs.put(bugObject.getId(), bugObject);

            System.out.println(bugObject);

            System.out.println("----------------------------");
        }
    }

    public void bugSolutionParsing ()
    {
        Element root = extractRootElement("C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\fixedDataHistory.xml");

        List<Element> listOfBugs = root.getChildren();

        for (int iteratorForListOfBugs = 0; iteratorForListOfBugs < listOfBugs.size(); iteratorForListOfBugs++)
        {
            Element bugElement = listOfBugs.get(iteratorForListOfBugs);

            List<Element> listOfElements = bugElement.getChildren();

            for (int iteratorForListOfElements = 0; iteratorForListOfElements < listOfElements.size(); iteratorForListOfElements++)
            {
                Element element = listOfElements.get(iteratorForListOfElements);

                if(element.getChild("what").getText().equals("Resolution"))
                {
                    if(mapOfDevelopers.containsKey(element.getChild("who").getText()))
                    {
                        mapOfDevelopers.get(element.getChild("who").getText()).getListOfBugIds().add(bugElement.getChild("bug_id").getText());
                    }
                    else
                    {
                        Developer developer = new Developer(element.getChild("who").getText(),
                                element.getChild("when").getText());
                        developer.getListOfBugIds().add(bugElement.getChild("bug_id").getText());
                    }
                }
            }
        }
    }

    public void parsing ()
    {
        bugDescriptionParsing();
        bugSolutionParsing();
    }
}
