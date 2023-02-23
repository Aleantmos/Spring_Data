import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _2_GetMinionNames {

    private static final String MINION_NAME_AND_AGE_BY_VILLAIN_ID = "select m.name, m.age " +
            "from minions as m " +
            "join minions_villains as mv on m.id = mv.minion_id " +
            "where villain_id = ?";

    private static final String VILLAIN_NAME_BY_ID = "select v.name from villains v where v.id = ?";

    private static final String NO_VILLAIN_FORMAT = "No villain with ID %d exists in the database.";

    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_AGE = "age";

    private static final String VILLAIN_FORMAT = "Villain: %s%n";
    private static final String MINION_FORMAT = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement villainStatement = connection.prepareStatement(VILLAIN_NAME_BY_ID);

        villainStatement.setInt(1, villainId);

        final ResultSet villainSet = villainStatement.executeQuery();


        if (!villainSet.next()) {
            System.out.printf(NO_VILLAIN_FORMAT, villainId);
            return;
        }

        final String villainName = villainSet.getString(COLUMN_LABEL_NAME);

        System.out.printf(VILLAIN_FORMAT, villainName);


        final PreparedStatement minionsStatement = connection.prepareStatement(MINION_NAME_AND_AGE_BY_VILLAIN_ID);

        minionsStatement.setInt(1, villainId);

        final ResultSet minionSet = minionsStatement.executeQuery();

        for (int i = 1; minionSet.next(); i++) {
            final String minionName = minionSet.getString(COLUMN_LABEL_NAME);
            final int minionAge = minionSet.getInt(COLUMN_LABEL_AGE);

            System.out.printf(MINION_FORMAT, i, minionName, minionAge);

        }
        connection.close();
    }
}
