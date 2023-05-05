package com.gamestory.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column(name = "trailer_id")
    private String trailerId;
    @Column(name = "image_url")
    private String url;
    @Column
    private Float size;
    @Column
    private BigDecimal price;
    @Column
    private String description;
    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {
    }

    public Game(String title,
                String trailerId,
                String imageThumbnail,
                Float size,
                BigDecimal price,
                String description,
                LocalDate releaseDate) {
        this.title = title;
        this.trailerId = trailerId;
        this.url = imageThumbnail;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
