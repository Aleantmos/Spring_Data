package softuni.bookshop.model.entity;

import jakarta.persistence.*;
import softuni.bookshop.model.enums.AgeRestriction;
import softuni.bookshop.model.enums.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated
    private EditionType editionType;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "copies")
    private Long copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "age_restriction")
    private AgeRestriction ageRestriction;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Set<Category> categories;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public Book setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Book setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getCopies() {
        return copies;
    }

    public Book setCopies(Long copies) {
        this.copies = copies;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public Book setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Book setCategories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }
}
