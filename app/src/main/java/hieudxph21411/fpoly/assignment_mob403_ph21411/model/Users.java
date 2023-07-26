package hieudxph21411.fpoly.assignment_mob403_ph21411.model;

public class Users {
    private String username;
    private String pass;
    private String email;
    private String fullname;
    private int role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Users() {
    }

    public Users(String username, String pass, String email, String fullname, int role) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
    }
}
