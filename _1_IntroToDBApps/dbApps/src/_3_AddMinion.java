import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class _3_AddMinion {


    public static final String GET_TOWN_BY_NAME = "select t.id from towns as t where t.name = ?";
    public static final String INSERT_INTO_TOWN = "insert into towns(name) values(?)";
    public static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";

    public static final String GET_VILLAIN_BY_NAME = "select v.id from villains as v where v.name = ?";
    public static final String INSERT_VILLAIN = "insert into villains (name, evilness_factor) values(?,?)";
    public static final String EVILNESS_FACTOR = "evil";
    public static final String VILLAIN_ADDED_FORMAT = "Villain %s was added to the database.%n";

    private static final String INSERT_INTO_MINIONS_VILLAINS = "insert into minions_villains(minion_id, villain_id) values(?,?)";

    private static final String INSERT_INTO_MINIONS = "insert into minions(name, age, town_id) values(?,?,?)";
    
    private static final String GET_LAST_MINION = "select m.id from minions as m order by m.id desc limit 1";

    private static final String COLUMN_LABEL_ID = "id";

    private static final String RESULT_FORMAT = "Successfully added %s to be minion of %s%n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        final Scanner scan = new Scanner(System.in);

        final String[] minionData = scan.nextLine().split("\\s+");

        final String minionName = minionData[1];
        final int minionAge = Integer.parseInt(minionData[2]);
        final String minionTown = minionData[3];

        String villainName = scan.nextLine().split("\\s+")[1];


        final int townId = getId(connection,
                List.of(minionTown),
                GET_TOWN_BY_NAME,
                INSERT_INTO_TOWN,
                TOWN_ADDED_FORMAT);

        final int villainId = getId(connection,
                List.of(villainName, EVILNESS_FACTOR),
                GET_VILLAIN_BY_NAME,
                INSERT_VILLAIN,
                VILLAIN_ADDED_FORMAT);

        final PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_INTO_MINIONS);

        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, townId);

        insertMinionStatement.executeUpdate();

        final PreparedStatement lastMinion = connection.prepareStatement(GET_LAST_MINION);

        final ResultSet lastMinionResultSet = lastMinion.executeQuery();

        lastMinionResultSet.next();

        int lastMinionId = lastMinionResultSet.getInt(COLUMN_LABEL_ID);

        final PreparedStatement insertIntoMinionsVillainsStatement = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);

        insertIntoMinionsVillainsStatement.setInt(1, lastMinionId);
        insertIntoMinionsVillainsStatement.setInt(2, villainId);

        insertIntoMinionsVillainsStatement.executeUpdate();

        System.out.printf(RESULT_FORMAT, minionName, villainName);

        connection.close();
    }

    private static int getId(Connection connection,
                             List<String> arguments,
                             String selectQuery,
                             String insertQuery,
                             String printFormat) throws SQLException {

        final String name = arguments.get(0);

        final PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, name);

        ResultSet resultSet = selectStatement.executeQuery();

        if (!resultSet.next()) {
            final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            for (int index = 1; index <= arguments.size(); index++) {
                insertStatement.setString(index, arguments.get(index - 1));
            }

            insertStatement.executeUpdate();

            final ResultSet newResultSet = selectStatement.executeQuery();
            newResultSet.next();

            System.out.printf(printFormat, name);

            return newResultSet.getInt(COLUMN_LABEL_ID);
        }


        return resultSet.getInt(COLUMN_LABEL_ID);

    }
}
