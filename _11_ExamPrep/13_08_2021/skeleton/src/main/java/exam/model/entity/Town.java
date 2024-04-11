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

    @OneToMany(mappedBy = "town")
    private List<Shop> shops;
    @OneToMany(mappedBy = "town")
    private List<Customer> customers;
}
