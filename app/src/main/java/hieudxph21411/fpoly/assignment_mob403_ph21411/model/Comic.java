package hieudxph21411.fpoly.assignment_mob403_ph21411.model;

public class Comic {
    private String author;
    private String[] content;
    private Cmt[] cmt;
    private String name;
    private String des;
    private int date;
    private String cover;

    public Comic(String author, String[] content, String name, String des, int date, String cover, int chapter) {
        this.author = author;
        this.content = content;
        this.name = name;
        this.des = des;
        this.date = date;
        this.cover = cover;
        this.chapter = chapter;
    }

    private int chapter;

    public Comic(String author, String[] content, Cmt[] cmt, String name, String des, int date, String cover, int chapter) {
        this.author = author;
        this.content = content;
        this.cmt = cmt;
        this.name = name;
        this.des = des;
        this.date = date;
        this.cover = cover;
        this.chapter = chapter;
    }

    public Comic() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public Cmt[] getCmt() {
        return cmt;
    }

    public void setCmt(Cmt[] cmt) {
        this.cmt = cmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
}
