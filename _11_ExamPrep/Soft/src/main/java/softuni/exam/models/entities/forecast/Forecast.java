package softuni.exam.models.entities.forecast;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.city.City;
import softuni.exam.models.entities.enums.DayENUM;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forecast")
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private DayENUM dayOfWeek;
    @Column(name = "max_temperature", nullable = false)
    @Min(-20)
    @Max(60)
    private Double maxTemperature;
    @Column(name = "min_temperature", nullable = false)
    @Min(-20)
    @Max(60)
    private Double minTemperature;
    @Column(nullable = false)
    private LocalTime sunrise;
    @Column(nullable = false)
    private LocalTime sunset;
    @OneToOne
    private City city;

}
