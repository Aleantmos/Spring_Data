import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Engine implements Runnable {

    private static final String UPDATE_TOWNS ="update Town as t set t.name = upper(t.name) where length(t.name) < 5";
    private static final String SELECT_EMPLOYEE ="select count(e) from Employee as e where e.firstName = :f_name and e.lastName = :l_name";

    private final EntityManager entityManager;
    private final BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select ex number:");

        try {
            int exNum = Integer.parseInt(bufferedReader.readLine());
            
            switch (exNum) {
                case 2: exTwo();
                break;
                case 3: exThree();
            }
                    
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    private void exThree() throws IOException {
        System.out.println("Enter employee full name:");

        final String[] fullName = bufferedReader.readLine().split("\\s+");

        final String firstName = fullName[0];

        final String lastName = fullName[1];

        Long singleResult = entityManager
                .createQuery(SELECT_EMPLOYEE, Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();


        System.out.println(singleResult == 0 ? "No" : "Yes");

    }

    private void exTwo() {
        entityManager.getTransaction().begin();

        int updateQuery = entityManager.createQuery(
                UPDATE_TOWNS).executeUpdate();

        System.out.println(updateQuery);

        entityManager.getTransaction().commit();
    }
}
