package exam.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Long population;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String travelGuide;

//    @OneToMany(mappedBy = "town")
//    private List<Shop> shops;
//    @OneToMany(mappedBy = "town")
//    private List<Customer> customers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }

//    public List<Shop> getShops() {
//        return shops;
//    }
//
//    public void setShops(List<Shop> shops) {
//        this.shops = shops;
//    }
//
//    public List<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(List<Customer> customers) {
//        this.customers = customers;
//    }
}
