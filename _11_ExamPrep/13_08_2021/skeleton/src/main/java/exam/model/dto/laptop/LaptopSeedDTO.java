package exam.model.dto.laptop;

import com.google.gson.annotations.Expose;
import exam.config.adapters.WarrantyTypeAdapter;
import exam.model.enums.WarrantyType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class LaptopSeedDTO {
    @Expose
    @Size(min = 8)
    private String macAddress;
    @Expose
    @Positive
    private Double cpuSpeed;
    @Expose
    @Min(value = 8)
    @Max(value = 128)
    private Integer ram;
    @Expose
    @Min(value = 128)
    @Max(value = 1024)
    private Integer storage;
    @Expose
    @Size(min = 10)
    private String description;
    @Expose
    @Positive
    private Double price;
    @Expose
    @XmlJavaTypeAdapter(WarrantyTypeAdapter.class)
    private WarrantyType warrantyType;
    @Expose
    private ShopNameDTO shop;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopNameDTO getShop() {
        return shop;
    }

    public void setShop(ShopNameDTO shop) {
        this.shop = shop;
    }
}

