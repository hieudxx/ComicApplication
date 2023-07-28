package hieudxph21411.fpoly.assignment_mob403_ph21411.model;


public class Users {
    private String _id;
    private String username;
    private String pass;
    private String email;
    private String fullname;
    private String avt;
    private int role;

    public Users() {
    }

    public Users(String username, String pass, String email, String fullname, String avt, int role) {

        this.username = username;
        this.pass = pass;
        this.email = email;
        this.fullname = fullname;
        this.avt = avt;
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
