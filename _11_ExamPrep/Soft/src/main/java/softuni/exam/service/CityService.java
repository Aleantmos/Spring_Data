package softuni.exam.service;

import softuni.exam.models.entities.city.City;

import java.io.IOException;

public interface CityService {

    boolean areImported();

    String readCitiesFileContent() throws IOException;
	
	String importCities() throws IOException;

    City getCityById(Long cityId);
}
