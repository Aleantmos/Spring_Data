import com.sun.jdi.connect.Connector;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException {


        Connection connection = MyConnector.getConnection();

        EntityManager<User> userManager = new EntityManager<>(connection);


        User user1 = new User("George", 14, LocalDate.now());
        userManager.persist(user1);

        user1.setAge(20);
        userManager.persist(user1);

        /*User user2 = new User("Ivan", 28, LocalDate.now());
        userManager.persist(user2);*/


    }
}
