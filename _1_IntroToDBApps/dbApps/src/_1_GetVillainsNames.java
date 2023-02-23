import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class _1_GetVillainsNames {

    private static final String VILLAIN_NAMES_STATEMENT = "select v.name, count(distinct mv.minion_id) as minions_count from villains as v " +
            "join minions_villains as mv on v.id = mv.villain_id " +
            "group by mv.villain_id " +
            "having minions_count > ? " +
            "order by minions_count;";

    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_MINIONS_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement statement =
                connection.prepareStatement(VILLAIN_NAMES_STATEMENT);

        statement.setInt(1, 15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            final String villainName = resultSet.getString(COLUMN_LABEL_NAME);
            final Integer minionsCount = resultSet.getInt(COLUMN_LABEL_MINIONS_COUNT);

            System.out.printf(PRINT_FORMAT, villainName, minionsCount);
        }
        connection.close();
    }
}
