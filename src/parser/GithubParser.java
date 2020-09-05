package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GithubParser
{
    private String url;
    private String unprocessedStringOfKeywords;

    public GithubParser(String url) {
        this.url = url;
        parseHTML();
    }

    public String getUnprocessedStringOfKeywords() {
        return unprocessedStringOfKeywords;
    }

    public void parseHTML ()
    {
        try {
            final Document document = Jsoup.connect(url).get();

            for (Element element : document.select("table.highlight.tab-size.js-file-line-container tr td.blob-code.blob-code-inner.js-file-line"))
            {
                System.out.println(element.text());
                //System.out.println("----------");
                /*if(element.attr("itemprop").equals("name codeRepository"))
                {
                    System.out.println(element.attr("href"));
                    System.out.println("----------");
                }*/
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
