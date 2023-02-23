import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _3_AddMinion {

    public static final String GET_VILLAIN = "select * from villains as v where v.name = ?";
    public static final String GET_MINION_TOWN_ID_BY_NAME = "select t.id from towns as t where t.name = ?";
    public static final String INSERT_INTO_TOWN = "insert into towns(name) values(?)";
    public static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";
    public static final String COLUMN_LABEL_ID = "id";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        final Scanner scan = new Scanner(System.in);

        final String[] minionData = scan.nextLine().split("\\s+");

        final String minionName = minionData[1];
        final int minionAge = Integer.parseInt(minionData[2]);
        final String minionTown = minionData[3];

        String villainName = scan.nextLine().split("\\s+")[1];

        final PreparedStatement townStatement = connection.prepareStatement(GET_MINION_TOWN_ID_BY_NAME);
        townStatement.setString(1, minionTown);

        ResultSet townSet = townStatement.executeQuery();

        if (!townSet.next()) {
            final PreparedStatement statement = connection.prepareStatement(INSERT_INTO_TOWN);
            statement.setString(1, minionTown);

            statement.executeUpdate();

            System.out.printf(TOWN_ADDED_FORMAT, minionTown);
        }

        ResultSet newTownSet = townStatement.executeQuery();

        newTownSet.getInt(COLUMN_LABEL_ID);

    }
}
