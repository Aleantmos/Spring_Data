package softuni.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Client extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int age;


    @OneToMany(mappedBy = "client", targetEntity = Account.class, cascade = CascadeType.MERGE)
    private Set<Account> accounts;

}
