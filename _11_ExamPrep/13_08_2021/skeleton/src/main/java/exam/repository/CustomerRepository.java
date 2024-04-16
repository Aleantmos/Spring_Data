package exam.repository;

import exam.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select count(c) = 0 from Customer c where c.email = :email")
    boolean checkEmailUniqueness(@Param("email") String email);
}
