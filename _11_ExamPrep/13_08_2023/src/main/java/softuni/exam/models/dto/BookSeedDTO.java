package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BookSeedDTO {
    @Expose
    @Size(min = 3, max = 40)
    private String author;
    @Expose
    private String available;
    @Expose
    @Size(min = 5)
    private String description;
    @Expose
    private String genre;
    @Expose
    @Size(min = 3, max = 40)
    private String title;
    @Expose
    @Positive
    private Double rating;

    public BookSeedDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
