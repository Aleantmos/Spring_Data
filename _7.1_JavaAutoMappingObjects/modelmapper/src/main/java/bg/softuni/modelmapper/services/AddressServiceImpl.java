package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.dto.AddressDto;
import bg.softuni.modelmapper.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(AddressDto data) {
        ModelMapper mapper = new ModelMapper();

        Address address = mapper.map(data, Address.class);

        return this.addressRepository.save(address);
    }
}
