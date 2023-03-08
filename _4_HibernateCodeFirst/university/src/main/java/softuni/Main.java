package softuni;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
    private static final String PERSISTENCE_UNIT = "university";
    public static void main(String[] args) {

        EntityManager entityManager = Persistence
                .createEntityManagerFactory(PERSISTENCE_UNIT)
                .createEntityManager();
    }
}