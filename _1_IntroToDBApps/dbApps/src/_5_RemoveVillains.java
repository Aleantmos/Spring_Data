import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class _5_RemoveVillains {
    private static final String GET_VILLAIN_BY_ID = "select v.name from villains as v where v.id = ?";
    private static final String GET_ALL_MINIONS_COUNT_BY_VILLAIN_ID = "select count(m.id) as minion_count from minions as m " +
            "join minions_villains mv on m.id = mv.minion_id " +
            "where villain_id = ?";
    private static final String DELETE_MINIONS_BY_VILLAIN_ID = "delete from minions_villains as mv " +
            "where villain_id = ?";
    private static final String DELETE_BY_VILLAIN_ID = "delete from villains as v " +
            "where v.id = ?";

    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_MINION_COUNT = "minion_count";

    private static final String NO_VILLAIN_MESSAGE = "No such villain was found";
    private static final String DELETED_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETED_COUNT_OF_MINIONS_FORMAT = "%d minions released";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement selectedVillain = connection.prepareStatement(GET_VILLAIN_BY_ID);
        selectedVillain.setInt(1, villainId);

        final ResultSet villainSet = selectedVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println(NO_VILLAIN_MESSAGE);
            connection.close();
            return;
        }

        final String villainName = villainSet.getString(COLUMN_LABEL_NAME);

        final PreparedStatement selectAllMinions = connection.prepareStatement(GET_ALL_MINIONS_COUNT_BY_VILLAIN_ID);
        selectAllMinions.setInt(1, villainId);

        ResultSet minionsCount = selectAllMinions.executeQuery();

        minionsCount.next();

        final int countOfMinionsSetFree = minionsCount.getInt(COLUMN_LABEL_MINION_COUNT);


        try(
                PreparedStatement deleteMinionStatement = connection.prepareStatement(DELETE_MINIONS_BY_VILLAIN_ID);
                PreparedStatement deleteVillainStatement = connection.prepareStatement(DELETE_BY_VILLAIN_ID)) {

            deleteMinionStatement.setInt(1, villainId);
            deleteMinionStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();

            connection.commit();
            System.out.printf(DELETED_VILLAIN_FORMAT, villainName);
            System.out.printf(DELETED_COUNT_OF_MINIONS_FORMAT, countOfMinionsSetFree);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        connection.close();

    }
}
