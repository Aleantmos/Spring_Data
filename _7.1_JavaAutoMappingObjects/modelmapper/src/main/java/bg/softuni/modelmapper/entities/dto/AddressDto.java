package bg.softuni.modelmapper.entities.dto;

public class AddressDto {

    private final String country;
    private final String city;

    public AddressDto(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }


    public String getCity() {
        return city;
    }

}
