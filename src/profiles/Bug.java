package profiles;

import java.util.ArrayList;
import java.util.List;

public class Bug
{
    private String id;
    private String creationDate;
    private String product;
    private String component;
    private String severity;
    private List<String> listOfKeywords = new ArrayList<>();
    private String solutionDate;

    public Bug()
    {

    }

    public Bug(String id, String creationDate, String product, String component, String severity) {
        this.id = id;
        this.creationDate = creationDate;
        this.product = product;
        this.component = component;
        this.severity = severity;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getProduct() {
        return product;
    }

    public String getComponent() {
        return component;
    }

    public String getSeverity() {
        return severity;
    }

    public List<String> getListOfKeywords() {
        return listOfKeywords;
    }

    public String getSolutionDate() {
        return solutionDate;
    }

    public void setSolutionDate(String solutionDate) {
        this.solutionDate = solutionDate;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id='" + id + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", product='" + product + '\'' +
                ", component='" + component + '\'' +
                ", severity='" + severity + '\'' +
                ", listOfKeywords=" + listOfKeywords +
                ", solutionDate='" + solutionDate + '\'' +
                '}';
    }
}
