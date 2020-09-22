package mainPackage;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
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
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        GithubParser gp = new GithubParser("https://github.com/bsse1006", LocalDate.parse("2018-06-01"));
        System.out.println(gp.getListOfLibraryImports().size());
        System.out.println(gp.getListOfRepositoryKeywords().size());

        /*XMLParser parser = new XMLParser();
        parser.parsing();

        Set<String> setOfProducts = new HashSet<>();

        for(Map.Entry m : parser.getMapOfBugs().entrySet())
        {
            Bug b = (Bug) m.getValue();
            //System.out.println(b.getProduct());
            setOfProducts.add(b.getComponent());
        }

        for(String p: setOfProducts)
        {
            System.out.println(p);
        }*/


        /*SourceCodeParser scp = new SourceCodeParser("C:\\Users\\Hp\\Desktop\\BugTriaging");

        for(String lib: scp.getListOfLibraryImports())
        {
            System.out.println(lib);
        }*/

        /*GithubParser githubParser = new GithubParser("https://github.com/jnunemaker", LocalDate.now());

        for (String keyword: githubParser.getListOfRepositoryKeywords())
        {
            System.out.println(keyword);
        }

        System.out.println("-----------------");

        for (String lib: githubParser.getListOfLibraryImports())
        {
            System.out.println(lib);
        }*/

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
            Developer developer = (Developer)d.getValue();
            System.out.println(developer.getName());
            counter++;
        }

        for(Map.Entry d: developers)
        {
            Developer developer = (Developer)d.getValue();
            System.out.println(developer.getStartDate());
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
