package profiles;

import java.util.HashSet;
import java.util.Set;

public class FreshGraduate
{
    private Developer developerCore;
    private Set<String> listOfKeyWords = new HashSet<>();

    public FreshGraduate(Developer developerCore) {
        this.developerCore = developerCore;
    }

    public Developer getDeveloperCore() {
        return developerCore;
    }

    public Set<String> getListOfKeyWords() {
        return listOfKeyWords;
    }


}
