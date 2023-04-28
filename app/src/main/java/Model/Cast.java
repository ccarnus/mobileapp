package Model;

public class Cast {

    private String title;
    private String description;
    private String department;
    private String category;
    private String type;
    private String brightmindid;
    private String casturl;
    private String university;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrightmindid(String brightmindid) {
        this.brightmindid = brightmindid;
    }

    public void setCasturl(String casturl) {
        this.casturl = casturl;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Cast(String title, String description, String department, String category, String type, String brightmindid, String casturl, String university){
        this.title = title;
        this.brightmindid = brightmindid;
        this.department = department;
        this.category = category;
        this.type = type;
        this.casturl = casturl;
        this.university = university;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getBrightmindid() {
        return brightmindid;
    }

    public String getCasturl() {
        return casturl;
    }

    public String getUniversity() {
        return university;
    }
}
