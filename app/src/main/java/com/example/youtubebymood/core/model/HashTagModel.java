package com.example.youtubebymood.core.model;

public class HashTagModel {
    private String tag;
    private String query;

    public HashTagModel(String tag, String query){
        this.tag = tag;
        this.query = query;
    }

    public String getTag() {
        return tag;
    }

    public String getQuery() {
        return query;
    }
}
