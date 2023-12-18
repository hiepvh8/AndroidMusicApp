package com.example.androidmusicapp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Song implements Serializable {
    @Expose
    @SerializedName("playCount")
    private int playCount;
    @Expose
    @SerializedName("coverArt")
    private String coverArt;
    @Expose
    @SerializedName("lyrics")
    private String lyrics;
    @Expose
    @SerializedName("year")
    private int year;
    @Expose
    @SerializedName("genre")
    private String genre;
    @Expose
    @SerializedName("duration")
    private int duration;
    @Expose
    @SerializedName("filePath")
    private String filePath;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id")
    private int id;

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(String coverArt) {
        this.coverArt = coverArt;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
