import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class _6_PrintAllMinionNames {

    private static final String SELECT_ALL_MINIONS = "select m.name from minions as m";
    private static final String COLUMN_LABEL_NAME = "name";

    public static void main(String[] args) throws SQLException {

        Connection connection = Utils.getSQLConnection();

        PreparedStatement minionsStatement = connection.prepareStatement(SELECT_ALL_MINIONS);
        ResultSet minionsSet = minionsStatement.executeQuery();

        ArrayDeque minionsNames = new ArrayDeque();

        while (minionsSet.next()) {
            minionsNames.add(minionsSet.getString(COLUMN_LABEL_NAME));
        }

        while (minionsNames.size() > 2) {
            System.out.println(minionsNames.pollFirst());
            System.out.println(minionsNames.pollLast());
        }

        while (!minionsNames.isEmpty()) {
            System.out.println(minionsNames.poll());
        }
        connection.close();
    }
    //thank u espinoza81

}
