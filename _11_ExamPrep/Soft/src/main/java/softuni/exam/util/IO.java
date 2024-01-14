package softuni.exam.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class IO {

    private static final Gson gson = new Gson();


    public static String reader(String path) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line)
                        .append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return content.toString();
    }

    public static <T> List<T> objectFromJson(Class<T[]> clazz, String path) {
        File file = new File(path);

        try (FileReader fileReader = new FileReader(file);) {

            List<T> dtoCollection = Arrays.asList(gson.fromJson(fileReader, clazz));
            fileReader.close();

            return dtoCollection;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
