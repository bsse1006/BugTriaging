package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class GithubParser
{
    private String url;
    private String unprocessedStringOfKeywords = "";

    public GithubParser(String url) {
        this.url = url;
        //parseHTML();
    }

    public String getUnprocessedStringOfKeywords() {
        return unprocessedStringOfKeywords;
    }

    public void parseFileNames (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            for (Element element : document.select("a.js-navigation-open.link-gray-dark"))
            {
                System.out.println(element.text());
                unprocessedStringOfKeywords = unprocessedStringOfKeywords + ' ' + element.text();
                parseFileNames("https://github.com" + element.attr("href"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseRepositories (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            for (Element element : document.select("a"))
            {
                if(element.attr("itemprop").equals("name codeRepository"))
                {
                    unprocessedStringOfKeywords = unprocessedStringOfKeywords + ' ' + element.text();
                    parseFileNames("https://github.com" + element.attr("href"));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parsePackages (String link)
    {

    }

    public void parseProjectData (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            for (Element element : document.select("div.project-columns.bg-white.d-flex.flex-auto.flex-column.clearfix.position-relative.no-wrap.project-touch-scrolling.js-project-columns "))
            {
                System.out.println(element.text());
                //unprocessedStringOfKeywords = unprocessedStringOfKeywords + ' ' + element.text();
                //parseProjectData("https://github.com" + element.attr("href"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseProjects (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            for (Element element : document.select("a.link-gray-dark.mr-1"))
            {
                unprocessedStringOfKeywords = unprocessedStringOfKeywords + ' ' + element.text();
                parseProjectData("https://github.com" + element.attr("href"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseHTML ()
    {
        try {
            final Document document = Jsoup.connect(url).get();

            String overviewLink = "";
            String repositoryLink = "";
            String projectLink = "";
            String packageLink = "";


            int counter = 0;
            for (Element element : document.select("a.UnderlineNav-item "))
            {
                if(counter == 4)
                    break;
                if(element.text().contains("Overview"))
                {
                    overviewLink = "https://github.com" + element.attr("href");
                }
                else if(element.text().contains("Repositories"))
                {
                    repositoryLink = "https://github.com" + element.attr("href");
                }
                else if(element.text().contains("Projects"))
                {
                    projectLink = "https://github.com" + element.attr("href");
                }
                else if(element.text().contains("Packages"))
                {
                    packageLink = "https://github.com" + element.attr("href");
                }
                counter++;
                //System.out.println("----------");
                /*if(element.attr("itemprop").equals("name codeRepository"))
                {
                    System.out.println(element.attr("href"));
                    System.out.println("----------");
                }*/
            }

            parseRepositories(repositoryLink);

            System.out.println(unprocessedStringOfKeywords);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
