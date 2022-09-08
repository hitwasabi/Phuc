package com.bkacad.tu.phuc;

public class Song {
    private String Title;
    private int File;
    private String Author;

    public Song(String title, int file, String author) {
        Title = title;
        File = file;
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}
