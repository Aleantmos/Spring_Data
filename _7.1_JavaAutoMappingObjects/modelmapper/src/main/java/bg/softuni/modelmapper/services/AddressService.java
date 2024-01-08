package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.dto.AddressDto;

public interface AddressService {

    Address create(AddressDto data);
}
