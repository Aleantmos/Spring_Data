package com.gamestory.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.gamestory.constants.Validations.*;

public class GameDTO {

    private String title;
    private String trailerId;
    private String url;
    private Float size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameDTO() {
    }

    public GameDTO(String title, String trailerId, String url, Float size, BigDecimal price, String description, LocalDate releaseDate) {
        setTitle(title);
        setTrailerId(trailerId);
        setUrl(url);
        setSize(size);
        setPrice(price);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null || Character.isUpperCase(title.charAt(0))
                && title.length() >= 3
                && title.length() <= 100) {
            throw new IllegalArgumentException(INVALID_GAME_TITLE_MESSAGE);
        }
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        if (trailerId != null && trailerId.length() != 11) {
            throw new IllegalArgumentException(INVALID_TRAILER_ID_MESSAGE);
        }
        this.trailerId = trailerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url != null && (!url.startsWith("http://") || !url.startsWith("https://"))) {
            throw new IllegalArgumentException(INVALID_LINK_MESSAGE);
        }
        this.url = url;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        if (size != null && size < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_OR_SIZE_MESSAGE);
        }
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.longValue() < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_OR_SIZE_MESSAGE);
        }
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() < 20) {
            throw new IllegalArgumentException(INVALID_DESCRIPTION_MESSAGE);
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


}
