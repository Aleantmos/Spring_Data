package softuni.exam.models.entities.city;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.BaseEntity;
import softuni.exam.models.entities.country.Country;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
@AttributeOverrides({
        @AttributeOverride(name="name",
                column = @Column(name = "city_name",
                        nullable = false,
                        unique = true))
})
public class City extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    @Size(min = 2)
    private String description;
    @Column(nullable = false)
    @Min(500)
    private Long population;
    @ManyToOne
    private Country country;
}
