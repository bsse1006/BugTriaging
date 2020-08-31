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
    private Map<String, Bug> mapOfBugs = new HashMap<>();
    private Map<String, Developer> mapOfDevelopers = new HashMap<>();

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

        Element root = document.getRootElement();

        return root;
    }

    private void bugDescriptionParsing ()
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
        }
    }

    private void bugSolutionParsing ()
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

                if(element.getName().equals("element"))
                {
                    if (element.getChild("what").getText().equals("Resolution"))
                    {
                        if (mapOfDevelopers.containsKey(element.getChild("who").getText()))
                        {
                            mapOfDevelopers.get(element.getChild("who").getText()).getListOfBugIds().add(bugElement.getChild("bug_id").getText());

                            //kahini ase, if bugs are not sorted by date
                        }
                        else
                        {
                            Developer developer = new Developer(element.getChild("who").getText(),
                                    element.getChild("when").getText());

                            developer.getListOfBugIds().add(bugElement.getChild("bug_id").getText());

                            mapOfDevelopers.put(developer.getName(), developer);
                        }

                        mapOfBugs.get(bugElement.getChild("bug_id").getText()).setSolutionDate(element.getChild("when").getText());

                        //kahini ase, multiple resolution er khetre kon date rakhbo shei bepar e
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

    public Map<String, Bug> getMapOfBugs() {
        return mapOfBugs;
    }

    public Map<String, Developer> getMapOfDevelopers() {
        return mapOfDevelopers;
    }

    @Override
    public String toString() {
        return "XMLParser{" +
                "mapOfBugs=" + mapOfBugs +
                ", mapOfDevelopers=" + mapOfDevelopers +
                '}';
    }
}
