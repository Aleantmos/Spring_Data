package exam.model.entity;

import exam.model.enums.WarrantyType;

import javax.persistence.*;

@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity {
    @Column(name = "mac_address", nullable = false, unique = true)
    private String macAddress;
    @Column(name = "cpu_speed", nullable = false)
    private Double cpuSpeed;
    @Column(name = "ram", nullable = false)
    private Integer ram;
    @Column(name = "storage", nullable = false)
    private Integer storage;
    @Column(name = "description", columnDefinition = "Text")
    private String description;
    @Column(name = "price")
    private Double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_type")
    private WarrantyType warrantyType;
    @ManyToOne
    private Shop shop;

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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
