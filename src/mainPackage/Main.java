package mainPackage;

import naturalLanguageProcessor.TextProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import parser.*;

import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import profiles.Bug;
import profiles.Developer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        GithubParser githubParser = new GithubParser("https://github.com/bsse1006/BugTriaging/blob/master/src/mainPackage/Main.java");

        /*XMLParser parser = new XMLParser();
        parser.parsing();
        //System.out.println(parser.toString());

        System.out.println("Number of Bugs: " + parser.getMapOfBugs().size());
        System.out.println("Number of Developers: " + parser.getMapOfDevelopers().size());
        System.out.println();

        List<Map.Entry<String, Developer>> developers = new ArrayList<>(parser.getMapOfDevelopers().entrySet());

        System.out.println("Oldest Developers: ");

        Collections.sort(developers, (o1, o2) -> o1.getValue().getStartDate().compareTo(o2.getValue().getStartDate()));

        int counter = 0;
        for(Map.Entry d: developers)
        {
            if(counter == 5)
            {
                break;
            }
            Developer developer = (Developer)d.getValue();
            System.out.println(developer.getName()+"----->"+developer.getStartDate());
            counter++;
        }

        System.out.println();

        System.out.println("Newest Developers: ");

        Collections.sort(developers, (o1, o2) -> o2.getValue().getStartDate().compareTo(o1.getValue().getStartDate()));

        counter = 0;
        for(Map.Entry d: developers)
        {
            if(counter == 5)
            {
                break;
            }
            Developer developer = (Developer)d.getValue();
            System.out.println(developer.getName()+"----->"+developer.getStartDate());
            counter++;
        }

        System.out.println();

        System.out.println("Top 5 Developers (Highest Amount of Bug Solution): ");

        Collections.sort(developers, (o1, o2) -> o2.getValue().getListOfBugIds().size() - o1.getValue().getListOfBugIds().size());

        counter = 0;
        for(Map.Entry d: developers)
        {
            if(counter == 5)
            {
                break;
            }
            Developer developer = (Developer)d.getValue();
            System.out.println(developer.getName()+"----->"+developer.getListOfBugIds().size());
            counter++;
        }*/
    }
}
