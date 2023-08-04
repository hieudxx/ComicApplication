package hieudxph21411.fpoly.assignment_mob403_ph21411.model;

public class Cmt {
    private String _id;
    private String time;
    private String content;
    private Comic comic;
    private Users users;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Cmt() {
    }

    public Cmt(String _id, String time, String content, Comic comic, Users users) {
        this._id = _id;
        this.time = time;
        this.content = content;
        this.comic = comic;
        this.users = users;
    }

    public Cmt(String time, String content) {
        this.time = time;
        this.content = content;
    }
}
