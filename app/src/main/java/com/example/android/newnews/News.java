package com.example.android.newnews;


public class News {

    private String title;
    private String newsUrl;
    private String section;
    private String date;
    private String author;

    public News(String t, String nu, String s, String d, String a) {
        title = t;
        newsUrl = nu;
        section = s;
        date = d;
        author = a;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return newsUrl;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

}
