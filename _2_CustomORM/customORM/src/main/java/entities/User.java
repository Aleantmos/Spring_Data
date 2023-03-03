package entities;

import lombok.Getter;
import lombok.Setter;
import orm.annotations.MyId;
import orm.annotations.MyColumn;
import orm.annotations.MyEntity;

import java.time.LocalDate;

@Getter
@Setter

@MyEntity(name = "users")
public class User {

    @MyId
    private Long id;

    @MyColumn(name = "username")
    private String username;

    @MyColumn(name = "age")
    private int age;

    @MyColumn(name = "registration_date")
    private LocalDate registrationDate;

    public User(String username, int age, LocalDate registrationDate) {
        this.username = username;
        this.age = age;
        this.registrationDate = registrationDate;
    }
}
