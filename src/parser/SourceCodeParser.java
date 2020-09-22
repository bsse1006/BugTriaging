package parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SourceCodeParser
{
    private List<String> listOfLibraryImports = new ArrayList<>();
    private String sourceCodeFolderPath;

    public SourceCodeParser (String sourceCodeFolderPath) throws FileNotFoundException {
        this.sourceCodeFolderPath = sourceCodeFolderPath;
        sourceCodeParser(sourceCodeFolderPath);
    }

    public List<String> getListOfLibraryImports() {
        return listOfLibraryImports;
    }

    public void parseJavaLibraries (String javaFIlePath) throws FileNotFoundException
    {
        File directoryPath = new File(javaFIlePath);
        Scanner sc = new Scanner(directoryPath);

        String javaFIleContent = "";

        while (sc.hasNextLine())
        {
            javaFIleContent = javaFIleContent + sc.nextLine() + "\n";
        }

        CompilationUnit cu = StaticJavaParser.parse(javaFIleContent);

        NodeList<ImportDeclaration> listOfImports = cu.getImports();

        for(ImportDeclaration i: listOfImports)
        {
            listOfLibraryImports.add(i.getName().toString());
        }
    }

    public void sourceCodeParser (String folderPath) throws FileNotFoundException
    {
        File directoryPath = new File(folderPath);

        File filesList [] = directoryPath.listFiles();

        if(filesList == null)
        {
            return;
        }

        for(File file : filesList)
        {
            if(file.getName().length()>5)
            {
                if(file.getName().substring(file.getName().length()-4, file.getName().length()).equals("java"))
                {
                    parseJavaLibraries(file.getAbsolutePath());
                }
            }

            sourceCodeParser(file.getAbsolutePath());
        }
    }
}