package com.jsonex.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsonex.domain.dto.products.wrappers.ProductsInRangeWithNoBuyerWrapperDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static com.jsonex.constants.Paths.PRODUCTS_WITHOUT_BUYERS_IN_RANGE_XML_PATH;

public enum Utils {
    ;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    public static <T> void writeJsonIntoFile(List<T> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(objects, fileWriter);

        fileWriter.flush();

        fileWriter.close();
    }

    public static <T> void writeXMLIntoFile(T data, Path filePath) throws IOException, JAXBException {
        final File file = filePath.toFile();

        JAXBContext context = JAXBContext.newInstance(data.getClass());
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(data, file);
    }

    public static <T> void writeJsonIntoFile(T object, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(object, fileWriter);

        fileWriter.flush();

        fileWriter.close();
    }
}
