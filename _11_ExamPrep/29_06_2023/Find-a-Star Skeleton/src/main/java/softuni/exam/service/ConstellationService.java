package softuni.exam.service;

import softuni.exam.models.entity.Constellation;

import java.io.IOException;

// TODO: Implement all methods

public interface ConstellationService {

    boolean areImported();

    String readConstellationsFromFile() throws IOException;

	String importConstellations() throws IOException;

    Constellation getConstellationById(Long constellation);
}
