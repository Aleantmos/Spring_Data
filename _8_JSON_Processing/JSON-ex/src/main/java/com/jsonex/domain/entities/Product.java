package com.jsonex.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User buyer;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User seller;
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;
}
