package exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.config.adapters.LocalDateAdapters;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                //.registerTypeAdapter(LocalDate.class, new LocalDateAdapters())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
