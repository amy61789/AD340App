package com.example.amyfunk.ad340app;

public class Movies {
    private String title;
    private String year;
    private String director;
    private String image;
    private String description;

    public Movies(String title, String year, String director, String image, String description){

        this.title = title;
        this.year = year;
        this.director = director;
        this.image = image;
        this.description = description;
    }

    public String getTitle(){
        return title;
    }

    public String getYear(){
        return year;
    }

    public String getDirector(){
        return director;
    }

    public String getImage(){
        return image;
    }

    public String getDescription(){
        return description;
    }
}
