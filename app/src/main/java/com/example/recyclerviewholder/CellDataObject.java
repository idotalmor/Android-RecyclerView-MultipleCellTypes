package com.example.recyclerviewholder;

public class CellDataObject {

    private String title,description,url;

    public CellDataObject(String title,String description,String url){
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
