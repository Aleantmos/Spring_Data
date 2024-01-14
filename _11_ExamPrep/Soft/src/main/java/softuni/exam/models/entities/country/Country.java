package softuni.exam.models.entities.country;

import softuni.exam.models.entities.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
@AttributeOverrides({
        @AttributeOverride(name="name",
                column = @Column(name = "country_name",
                        unique = true,
                        nullable = false))
})
public class Country extends BaseEntity {

    @Size(min = 2, max = 60)
    @Column(nullable = false)
    private String currency;

}
