package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.BorrowingRecordProjection;
import softuni.exam.models.entity.BorrowingRecord;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query("select b.title as title , b.author as author, br.borrowDate as borrowDate, concat(lb.firstName,' ', lb.lastName) as libraryMemberName  " +
            "from BorrowingRecord br " +
            "left join Book b on br.book.id = b.id " +
            "left join LibraryMember lb on br.libraryMember.id = b.id " +
            "where b.genre = 'SCIENCE_FICTION' " +
            "order by br.borrowDate desc" )
    List<BorrowingRecordProjection> getRecordsBeforeData(LocalDate beforeDate);
}
