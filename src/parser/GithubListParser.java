package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GithubListParser
{
    private Map<String, String> mapOfDevelopersWithGithubURLs = new HashMap<>();

    public Map<String, String> getMapOfDevelopersWithGithubURLs() {
        return mapOfDevelopersWithGithubURLs;
    }

    public void parseGithubList () throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get("C:\\Users\\Hp\\Desktop\\BugTriaging\\src\\files\\githubURL.txt")));

        StringTokenizer st = new StringTokenizer(data);
        while (st.hasMoreTokens())
        {
            mapOfDevelopersWithGithubURLs.put(st.nextToken(),st.nextToken());
        }
    }
}
