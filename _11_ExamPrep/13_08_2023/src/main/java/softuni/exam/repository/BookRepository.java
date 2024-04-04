package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book getBookByTitle(String title);

    @Query("select count(b) > 0 from Book b where b.title = :name")
    boolean existsByTitle(@Param("name") String name);
}
