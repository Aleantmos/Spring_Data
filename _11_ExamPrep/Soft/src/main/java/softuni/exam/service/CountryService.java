package softuni.exam.service;


import softuni.exam.models.entities.country.Country;

import java.io.IOException;
import java.util.Optional;

// TODO: Implement all methods
public interface CountryService {

    boolean areImported();

    String readCountriesFromFile() throws IOException;
	
	String importCountries() throws IOException;

    Country getCountryById(Long id);
}
