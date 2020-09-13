package testing;

import parser.GithubListParser;
import parser.XMLParser;
import profiles.Bug;
import profiles.Developer;
import profiles.FreshGraduate;
import profiles.NewDeveloper;

import java.time.LocalDate;
import java.util.*;

public class Test
{
    private LocalDate testingDate;
    private List<Developer> experiencedDevelopers = new ArrayList<>();
    private List<NewDeveloper> newExperiencedDevelopers = new ArrayList<>();
    private List<FreshGraduate> freshGraduates = new ArrayList<>();
    private Map<String, Bug> mapOfBugs;
    private Set<String> listOfSourceCodeLibraryImports;
    private Map<String, String> mapOfDevelopersWithGithubURLs = new HashMap<>();

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

        Collections.shuffle(list, new Random(list.size()));

        list = list.subList(0, list.size()/10);

        FreshGraduate fg = new FreshGraduate(developer);
        fg.getListOfKeyWords().addAll(list);

        freshGraduates.add(fg);
    }

    public void chooseNewDeveloperOrFreshGraduate (Developer developer)
    {
        if(mapOfDevelopersWithGithubURLs.get(developer.getName()).equals("0"))
        {
            createFreshGraduate(developer);
        }
        else
        {
            //NED based on Github  Link
        }
    }

    public void createBugsAndED () throws Exception {
        XMLParser parser = new XMLParser();
        parser.parsing();
        this.mapOfBugs = parser.getMapOfBugs();

        GithubListParser glp = new GithubListParser();
        glp.parseGithubList();
        this.mapOfDevelopersWithGithubURLs = glp.getMapOfDevelopersWithGithubURLs();

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
        }
    }
}
