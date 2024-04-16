package exam.repository;

import exam.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("select count(s) = 0 from Shop s where s.name = :name")
    boolean checkNameUniqueness(@Param("name") String name);

    @Query("select s from Shop s where s.name = :name")
    Shop getShopByName(@Param("name") String name);
}
