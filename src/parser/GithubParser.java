package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import naturalLanguageProcessor.TextProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GithubParser
{
    private String url;
    private Set<String> listOfRepositoryKeywords = new HashSet<>();
    private Set<String> listOfLibraryImports = new HashSet<>();
    private String unprocessedStringOfKeywords = "";

    public GithubParser(String url) {
        this.url = url;
        parseHTML();
    }

    public Set<String> getListOfRepositoryKeywords() {
        return listOfRepositoryKeywords;
    }

    public Set<String> getListOfLibraryImports() {
        return listOfLibraryImports;
    }

    public void parseJavaLibraries (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            String javaFile = "";

            for (Element element : document.select("td.blob-code.blob-code-inner.js-file-line"))
            {
                javaFile = javaFile + element.text() + "\n";
            }

            CompilationUnit cu = StaticJavaParser.parse(javaFile);

            NodeList<ImportDeclaration> listOfImports = cu.getImports();

            for(ImportDeclaration i: listOfImports)
            {
                listOfLibraryImports.add(i.getName().toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseReadme (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            for (Element element : document.select("article.markdown-body.entry-content.container-lg"))
            {
                for(Element p: element.select("p"))
                {
                    unprocessedStringOfKeywords = unprocessedStringOfKeywords + ' ' + p.text();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseFileNames (String link)
    {
        try {
            final Document document = Jsoup.connect(link).get();

            for (Element element : document.select("a.js-navigation-open.link-gray-dark"))
            {
                if(element.text().length()>5)
                {
                    if(element.text().substring(element.text().length()-4, element.text().length()).equals("java"))
                    {
                        parseJavaLibraries("https://github.com" + element.attr("href"));
                    }
                }
                if(element.text().equals("README.md"))
                {
                    parseReadme("https://github.com" + element.attr("href"));
                }
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

            for (Element element : document.select("div.col-10.col-lg-9.d-inline-block"))
            {
                for(Element span : element.select("span"))
                {
                    if(span.attr("itemprop").equals("programmingLanguage"))
                    {
                        if(span.text().equals("Java"))
                        {
                            for (Element repo: element.select("a"))
                            {
                                if(repo.attr("itemprop").equals("name codeRepository"))
                                {
                                    unprocessedStringOfKeywords = unprocessedStringOfKeywords + ' ' + repo.text();
                                    parseFileNames("https://github.com" + repo.attr("href"));
                                }
                            }
                        }
                    }
                }
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

            String repositoryLink = "";

            for (Element element : document.select("a.UnderlineNav-item "))
            {
                if(element.text().contains("Repositories"))
                {
                    repositoryLink = "https://github.com" + element.attr("href");
                    break;
                }
            }

            parseRepositories(repositoryLink);

            processStringOfKeywords();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processStringOfKeywords() throws Exception
    {
        TextProcessor tp = new TextProcessor(unprocessedStringOfKeywords);
        listOfRepositoryKeywords = tp.getKeywords();
    }
}
