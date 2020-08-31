package profiles;

import java.util.ArrayList;
import java.util.List;

public class Developer
{
    private String name;
    private String startDate;
    private List<String> listOfBugIds = new ArrayList<>();

    public Developer()
    {

    }

    public Developer(String name, String startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public List<String> getListOfBugIds() {
        return listOfBugIds;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", listOfBugIds=" + listOfBugIds +
                '}';
    }
}
