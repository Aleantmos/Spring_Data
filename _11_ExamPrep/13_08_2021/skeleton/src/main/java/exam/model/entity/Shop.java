package exam.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop extends BaseEntity {
    @Column(nullable = false)
    private String address;
    @Column(name = "employee_count", nullable = false)
    private Integer employeeCount;
    @Column(nullable = false)
    private Double income;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "shop_area", nullable = false)
    private Integer shopArea;
    @OneToMany(mappedBy = "shop")
    private List<Laptop> laptops;

    @ManyToOne
    private Town town;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
