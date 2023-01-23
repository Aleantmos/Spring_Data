package softuni.entities;


import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column
    @Min(0)
    private BigDecimal balance;

    @ManyToOne
    private Client client;

}
