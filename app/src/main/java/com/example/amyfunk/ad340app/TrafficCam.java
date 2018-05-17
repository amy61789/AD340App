package com.example.amyfunk.ad340app;

public class TrafficCam {
    private String description;
    private String imageUrl;
    private String type;
    private double[] coords;

    public TrafficCam(String description, String imageUrl, String type, double[] coords){

        this.description = description;
        this.imageUrl = imageUrl;
        this.type = type;
        this.coords = coords;

    }
    public String getDescription(){
        return description;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getType(){
        return type;
    }

    public double[] getCoords() {
        return coords;
    }
}
