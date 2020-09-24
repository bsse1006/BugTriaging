package testing;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import parser.GithubListParser;
import parser.GithubParser;
import parser.SourceCodeParser;
import parser.XMLParser;
import profiles.Bug;
import profiles.Developer;
import profiles.FreshGraduate;
import profiles.NewDeveloper;

import javax.print.Doc;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static org.apache.lucene.util.Version.LUCENE_8_6_2;

public class Test
{
    private LocalDate testingDate;
    private List<Developer> experiencedDevelopers = new ArrayList<>();
    private List<NewDeveloper> newExperiencedDevelopers = new ArrayList<>();
    private List<FreshGraduate> freshGraduates = new ArrayList<>();
    private Map<String, Bug> mapOfBugs;
    private List<String> listOfSourceCodeLibraryImports;
    private Map<String, String> mapOfDevelopersWithGithubURLs = new HashMap<>();
    private List<Bug> testBugs = new ArrayList<>();

    public Test(LocalDate testingDate)
    {
        this.testingDate = testingDate;
    }

    public void createFreshGraduate (Developer developer)
    {
        List<String> list = new ArrayList<>();

        for(String bugID: developer.getListOfBugIds())
        {
            list.addAll(mapOfBugs.get(bugID).getListOfKeywords());
        }

        Collections.shuffle(list);

        list = list.subList(0, list.size()/10);

        FreshGraduate fg = new FreshGraduate(developer);
        fg.getListOfKeyWords().addAll(list);

        freshGraduates.add(fg);
    }

    public void chooseNewDeveloperOrFreshGraduate (Developer developer) throws InterruptedException
    {
        System.out.println("-----" + mapOfDevelopersWithGithubURLs.get(developer.getName()));
        if(mapOfDevelopersWithGithubURLs.get(developer.getName()).equals("0"))
        {
            createFreshGraduate(developer);
        }
        else
        {
            GithubParser gp = new GithubParser(mapOfDevelopersWithGithubURLs.get(developer.getName()),LocalDate.parse("2013-08-15"));
            if(gp.getListOfRepositoryKeywords().size()==0||gp.getListOfLibraryImports().size()==0)
            {
                createFreshGraduate(developer);
            }
            else
            {
                newExperiencedDevelopers.add(new NewDeveloper(developer,gp.getListOfRepositoryKeywords(),gp.getListOfLibraryImports()));
            }
        }
        System.out.println("cp2");
    }

    public void testing () throws Exception {
        XMLParser parser = new XMLParser();
        parser.parsing();
        this.mapOfBugs = parser.getMapOfBugs();

        GithubListParser glp = new GithubListParser();
        glp.parseGithubList();
        this.mapOfDevelopersWithGithubURLs = glp.getMapOfDevelopersWithGithubURLs();

        System.out.println("cp");

        for(Developer developer: parser.getMapOfDevelopers().values())
        {
            if(developer.getStartDate().compareTo(testingDate)<0)
            {
                experiencedDevelopers.add(developer);
            }
            else
            {
                chooseNewDeveloperOrFreshGraduate(developer);
            }
            System.out.println("cp1");
        }

        for(Map.Entry b: mapOfBugs.entrySet())
        {
            Bug bug = (Bug) b.getValue();

            if(bug.getSolutionDate().compareTo(testingDate) > 0)
            {
                testBugs.add(bug);
            }
        }

        System.out.println("cp3");

        SourceCodeParser scp = new SourceCodeParser("C:\\Users\\Hp\\Desktop\\BugTriaging");

        listOfSourceCodeLibraryImports = scp.getListOfLibraryImports();

        indexing ();
    }

    private void indexing() throws IOException, ParseException {
        edIndexing();
        nedIndexing();
        fgIndexing();
        readEdIndex();
        System.out.println("||||||||||||||||");
        //System.out.println(newExperiencedDevelopers.size());
        readNedIndex();
        System.out.println("||||||||||||||||");
        //System.out.println(freshGraduates.size());
        readFgIndex();
    }

    public void readEdIndex () throws IOException, ParseException {
        String indexPath = "C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\edIndex";

        Directory dir = FSDirectory.open(Paths.get(indexPath));

        DirectoryReader directoryReader = DirectoryReader.open(dir);

        IndexSearcher searcher = new IndexSearcher (directoryReader);

        BooleanQuery.Builder bq = new BooleanQuery.Builder();

        for (String s: testBugs.get(0).getListOfKeywords())
        {
            bq.add(new BooleanClause(new TermQuery(new Term("content", s)), BooleanClause.Occur.SHOULD));
        }

        //QueryParser qp = new QueryParser("content", new StandardAnalyzer());

        //System.out.println(convertListToQuery(testBugs.get(0).getListOfKeywords()));

        //Query query = qp.parse(convertListToQuery(testBugs.get(0).getListOfKeywords())); //syntax

        TopDocs results = searcher.search(bq.build(), 20);

        for(ScoreDoc scoreDoc: results.scoreDocs)
        {
            Document document = searcher.doc(scoreDoc.doc);
            System.out.println(document.get("name"));
            System.out.println(scoreDoc.doc);
            System.out.println(scoreDoc.score);
            System.out.println("----------");
        }

        directoryReader.close();
    }

    private void readNedIndex () throws IOException, ParseException {
        String indexPath = "C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\nedIndex";

        Directory dir = FSDirectory.open(Paths.get(indexPath));

        DirectoryReader directoryReader = DirectoryReader.open(dir);

        IndexSearcher searcher = new IndexSearcher (directoryReader);

        BooleanQuery.Builder bq = new BooleanQuery.Builder();

        for (String s: testBugs.get(0).getListOfKeywords())
        {
            bq.add(new BooleanClause(new TermQuery(new Term("content", s)), BooleanClause.Occur.SHOULD));
        }

        for (String s: listOfSourceCodeLibraryImports)
        {
            bq.add(new BooleanClause(new TermQuery(new Term("content", s)), BooleanClause.Occur.SHOULD));
        }

        TopDocs results = searcher.search(bq.build(), 5);

        for(ScoreDoc scoreDoc: results.scoreDocs)
        {
            Document document = searcher.doc(scoreDoc.doc);
            System.out.println(document.get("name"));
            System.out.println(scoreDoc.doc);
            System.out.println(scoreDoc.score);
        }

        directoryReader.close();
    }

    private void readFgIndex () throws IOException, ParseException {
        String indexPath = "C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\fgIndex";

        Directory dir = FSDirectory.open(Paths.get(indexPath));

        DirectoryReader directoryReader = DirectoryReader.open(dir);

        IndexSearcher searcher = new IndexSearcher (directoryReader);

        BooleanQuery.Builder bq = new BooleanQuery.Builder();

        for (String s: testBugs.get(0).getListOfKeywords())
        {
            bq.add(new BooleanClause(new TermQuery(new Term("content", s)), BooleanClause.Occur.SHOULD));
        }

        TopDocs results = searcher.search(bq.build(), 5);

        for(ScoreDoc scoreDoc: results.scoreDocs)
        {
            Document document = searcher.doc(scoreDoc.doc);
            System.out.println(document.get("name"));
            System.out.println(scoreDoc.doc);
            System.out.println(scoreDoc.score);
        }

        directoryReader.close();
    }

    private String convertListToString (List<String> list)
    {
        String listString = "";

        for(String s: list)
        {
            listString = listString + s + " ";
        }

        return listString;
    }

    /*private String convertListToQuery (List<String> list)
    {
        String queryString = "";

        for(String s: list)
        {
            queryString = queryString + "content:" + s + " OR ";
        }

        queryString = queryString.substring(0,queryString.length()-4);

        return queryString;
    }*/

    public void edIndexing() throws IOException {
        String indexPath = "C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\edIndex";

        Directory dir = FSDirectory.open(Paths.get(indexPath));

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(dir, iwc);

        for(Developer developer: experiencedDevelopers)
        {
            Document document = new Document();

            String content = "";

            for(String bugID: developer.getListOfBugIds())
            {
                Bug bug = mapOfBugs.get(bugID);
                if (bug.getSolutionDate().compareTo(testingDate) < 0)
                {
                    content = content + " " + convertListToString(bug.getListOfKeywords());
                }
            }

            document.add(new TextField("content", content, Field.Store.NO));
            document.add(new StringField("name", developer.getName(), Field.Store.YES));
            document.add(new StringField("startingDate", developer.getStartDate().toString(), Field.Store.YES));

            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }

    private void nedIndexing() throws IOException {
        String indexPath = "C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\nedIndex";

        Directory dir = FSDirectory.open(Paths.get(indexPath));

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(dir, iwc);

        for(NewDeveloper developer: newExperiencedDevelopers)
        {
            Document document = new Document();

            String content = "";

            content = content + " " + convertListToString(developer.getListOfLibraryImports());
            content = content + " " + convertListToString(developer.getListOfRepositoryKeywords());


            document.add(new TextField("content", content, Field.Store.NO));
            document.add(new StringField("name", developer.getDeveloperCore().getName(), Field.Store.YES));
            document.add(new StringField("startingDate", developer.getDeveloperCore().getStartDate().toString(), Field.Store.YES));

            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }

    private void fgIndexing() throws IOException {
        String indexPath = "C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\fgIndex";

        Directory dir = FSDirectory.open(Paths.get(indexPath));

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(dir, iwc);

        for(FreshGraduate developer: freshGraduates)
        {
            Document document = new Document();

            String content = "";

            content = content + " " + convertListToString(developer.getListOfKeyWords());


            document.add(new TextField("content", content, Field.Store.NO));
            document.add(new StringField("name", developer.getDeveloperCore().getName(), Field.Store.YES));
            document.add(new StringField("startingDate", developer.getDeveloperCore().getStartDate().toString(), Field.Store.YES));

            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }
}
