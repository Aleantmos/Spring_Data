package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import orm.annotations.Entity;

import java.time.LocalDate;

@Getter
@Setter

@Entity(name = "users")
public class User {

    private long id;

    private String username;

    private int age;

    private LocalDate localDate;

    public User(String username, int age, LocalDate localDate) {
        this.username = username;
        this.age = age;
        this.localDate = localDate;
    }
}
