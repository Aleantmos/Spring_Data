package softuni;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import softuni.entity.Product;
import softuni.entity.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;

public class  Main {
    public static void main(String[] args) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("salesDB")
                .createEntityManager();


        entityManager.getTransaction().begin();

        /*Sale sale = new Sale();
        sale.setDate(LocalDate.now());

        Product product = new Product();
        product.setName("testProduct");
        product.setPrice(BigDecimal.TEN);
        product.setQuantity(5.0);

        product.getSales().add(sale);
        sale.setProduct(product);

        entityManager.persist(product);
*/
        /*
        Product found = entityManager.find(Product.class, 1);

        entityManager.remove(found);
*/
        entityManager.getTransaction().commit();

    }
}