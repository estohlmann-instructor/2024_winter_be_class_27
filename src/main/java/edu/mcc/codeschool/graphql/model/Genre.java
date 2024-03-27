package edu.mcc.codeschool.graphql.model;

public class Genre {
    private String genre;
    private String description;

    public String getGenre() {
        return genre;
    }

    public Genre setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Genre setDescription(String description) {
        this.description = description;
        return this;
    }
}
