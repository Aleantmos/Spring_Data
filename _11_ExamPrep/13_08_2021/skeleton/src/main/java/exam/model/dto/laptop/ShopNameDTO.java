package exam.model.dto.laptop;

import com.google.gson.annotations.Expose;

public class ShopNameDTO {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
