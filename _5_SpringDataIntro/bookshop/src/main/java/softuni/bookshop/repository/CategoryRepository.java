package softuni.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bookshop.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
