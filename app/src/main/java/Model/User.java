package Model;

public class User {

    private String username;
    private String email;
    private String role;
    private String department;
    private int score;

    public User(String email, String username, String role, String department, int score){
        this.username = username;
        this.email = email;
        this.role = role;
        this.department = department;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public int getScore() {
        return score;
    }
}
