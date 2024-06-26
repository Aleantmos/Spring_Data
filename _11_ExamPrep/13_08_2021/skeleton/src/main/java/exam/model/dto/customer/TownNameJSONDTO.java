package exam.model.dto.customer;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class TownNameJSONDTO {
    @Expose
    @Size(min = 2)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
