package com.example.artvilla;

public class items {
    private String item_name;
    private String artist_name;
    private String artist_mono;
    private String PhotoPath;

    public items()
    {
    }

    public items(String item_name, String artist_name, String artist_mono, String photoPath) {
        this.item_name = item_name;
        this.artist_name = artist_name;
        this.artist_mono = artist_mono;
        PhotoPath = photoPath;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getartist_mono() {
        return artist_mono;
    }

    public void setartist_mono(String artist_mono) {
        this.artist_mono = artist_mono;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }
}
