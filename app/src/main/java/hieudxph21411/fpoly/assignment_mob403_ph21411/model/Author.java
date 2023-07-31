package hieudxph21411.fpoly.assignment_mob403_ph21411.model;

public class Author {
    private String _id;
    private String name;
    private Comic[] comic;
    private String date;

    public Author(String _id, String name, Comic[] comic, String date) {
        this._id = _id;
        this.name = name;
        this.comic = comic;
        this.date = date;
    }

    public Author() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Comic[] getComic() {
        return comic;
    }

    public void setComic(Comic[] comic) {
        this.comic = comic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
