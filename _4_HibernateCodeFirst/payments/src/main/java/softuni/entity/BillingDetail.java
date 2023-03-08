package softuni.entity;

import javax.persistence.*;

@Entity
@Table(name = "billing_detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class BillingDetail extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String number;

    @ManyToOne
    private User owner;

    public BillingDetail() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
