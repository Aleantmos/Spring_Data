package exam.model.dto.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import exam.config.adapters.LocalDateAdapters;
import exam.model.dto.shop.TownNameXMLDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CustomerSeedDTO {
    @Expose
    @Size(min = 2)
    private String firstName;
    @Expose
    @Size(min = 2)
    private String lastName;
    @Expose
    @Email
    //unique
    private String email;
    @Expose
    @JsonAdapter(LocalDateAdapters.class)
    private LocalDate registeredOn;
    @Expose
    private TownNameJSONDTO town;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public TownNameJSONDTO getTown() {
        return town;
    }

    public void setTown(TownNameJSONDTO town) {
        this.town = town;
    }
}
