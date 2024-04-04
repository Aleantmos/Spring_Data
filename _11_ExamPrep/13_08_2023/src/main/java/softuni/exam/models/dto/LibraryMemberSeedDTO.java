package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LibraryMemberSeedDTO {
    @Expose
    @Size(min = 2, max = 30)
    private String address;
    @Expose
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;
    @Expose
    @NotNull
    @Size(min = 2, max = 40)
    private String lastName;
    @Expose
    @Size(min = 2, max = 20)
    private String phoneNumber;

    public LibraryMemberSeedDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
