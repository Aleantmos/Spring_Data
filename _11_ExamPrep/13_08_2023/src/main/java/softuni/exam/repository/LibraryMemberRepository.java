package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.LibraryMember;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {

    @Query("select l from LibraryMember as l where l.id = :id")
    LibraryMember getMemberById(@Param("id")long id);


    @Query("SELECT COUNT(l) > 0 FROM LibraryMember l WHERE l.phoneNumber = :phoneNumber")
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
