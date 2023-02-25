import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class _4_ChangeTownNamesCasing {

    private static final String UPDATE_TOWN_NAME = "update towns as t set name = upper(name) where t.country = ?";
    private static final String GET_ALL_TOWN_NAMES_BY_COUNTRY = "select t.name from towns as t where t.country = ?";

    private static final String TOWNS_COUNT_FORMAT = "%d town names were affected.%n";
    private static final String NO_TOWNS_FORMAT = "No town names were affected.";
    private static final String COLUMN_LABEL_NAME = "name";
    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final String townName = new Scanner(System.in).nextLine();

        final PreparedStatement statement = connection.prepareStatement(UPDATE_TOWN_NAME);
        statement.setString(1, townName);

        final int townsAffected = statement.executeUpdate();

        if (townsAffected == 0) {
            System.out.println(NO_TOWNS_FORMAT);
            connection.close();
            return;
        }

        System.out.printf(TOWNS_COUNT_FORMAT, townsAffected);

        final PreparedStatement selectAllTownsFromCountry = connection.prepareStatement(GET_ALL_TOWN_NAMES_BY_COUNTRY);
        selectAllTownsFromCountry.setString(1, townName);

        final ResultSet townsResultSet = selectAllTownsFromCountry.executeQuery();

        ArrayList<String> towns = new ArrayList<>();

        while (townsResultSet.next()) {
            towns.add(townsResultSet.getString(COLUMN_LABEL_NAME));
        }

        System.out.println(towns);
    }
}
