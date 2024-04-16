package exam.repository;

import exam.model.dto.BestLaptopProjection;
import exam.model.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    @Query("select count(l) = 0 from Laptop l where l.macAddress = :address")
    boolean checkMacAddressUniqueness(@Param("address") String macAddress);

    @Query("select l.macAddress as macAddress, round(2, l.cpuSpeed) as cpuSpeed, " +
            "l.ram as ram, l.storage as storage, round(2, l.price) as price, s.name as shopName, t.name as townName " +
            "from Laptop l " +
            "left join Shop s on s.id = l.shop.id " +
            "left join Town t on s.town.id = t.id " +
            "order by l.cpuSpeed desc, l.ram desc, l.storage desc, l.macAddress")
    List<BestLaptopProjection> getBestLaptops();
}
