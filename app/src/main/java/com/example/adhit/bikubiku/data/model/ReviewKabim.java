package com.example.adhit.bikubiku.data.model;

/**
 * Created by roy on 12/6/2017.
 */

public class ReviewKabim {
    private String stars;
    private String body;
    private String authors;

    public ReviewKabim(String stars, String body, String authors) {
        this.stars = stars;
        this.body = body;
        this.authors = authors;
    }

    public String getStars() {
        return stars;
    }

    public String getBody() {
        return body;
    }

    public String getAuthors() {
        return authors;
    }
}
