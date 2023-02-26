import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class _7_IncreaseMinionsAge {

    private static final String CHANGE_MINION_NAME_AND_AGE = "update minions as m set m.age = m.age + 1, m.name = lower(m.name) where m.id = ?";
    private static final String SELECT_MINIONS_NAME_AND_AGE = "select m.name, m.age from minions as m";

    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_AGE = "age";

    private static final String MINION_PRINT_FORMAT = "%s %d%n";
    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        Integer[] minionIds = Arrays.stream(new Scanner(System.in).nextLine().split("\\s+"))
                .map(input -> Integer.parseInt(input))
                .toArray(Integer[]::new);

        PreparedStatement changeMinionNameAndAgeStatement = connection.prepareStatement(CHANGE_MINION_NAME_AND_AGE);

        for (int i = 0; i < minionIds.length; i++) {

            changeMinionNameAndAgeStatement.setInt(1, minionIds[i]);
            changeMinionNameAndAgeStatement.executeUpdate();
        }

        PreparedStatement allMinionsUpdatedStatement = connection.prepareStatement(SELECT_MINIONS_NAME_AND_AGE);
        ResultSet minionSet = allMinionsUpdatedStatement.executeQuery();

        while (minionSet.next()) {
            String minionName = minionSet.getString(COLUMN_LABEL_NAME);
            int minionAge = minionSet.getInt(COLUMN_LABEL_AGE);
            System.out.printf(MINION_PRINT_FORMAT, minionName, minionAge);
        }


    }
}
