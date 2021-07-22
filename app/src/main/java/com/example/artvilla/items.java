package com.example.artvilla;

public class items {
    String item_name;
    String artist_name;
    String price;
    String PhotoPath;

    public items()
    {
    }

    public items(String item_name, String artist_name, String price, String photoPath) {
        this.item_name = item_name;
        this.artist_name = artist_name;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }
}
