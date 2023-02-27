package orm;

import orm.annotations.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntityManager<E> implements DbContext<E> {

    private static final String INSERT_INTO = "insert into %s (%s) values (%s)";

    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException {

        String tableName = getTableName(entity);

        /*String fieldList = getDBFieldsWithoutIdentity(entity);

        String valueList = getInsertValues(entity);*/

        PreparedStatement statement = connection.prepareStatement(String.format(INSERT_INTO, tableName, "fieldList", "valueList"));

        return statement.execute();

    }

    @Override
    public Iterable<E> find() {
        return null;
    }

    @Override
    public Iterable<E> find(String where) {
        return null;
    }

    @Override
    public Iterable<E> findFirst() {
        return null;
    }

    @Override
    public Iterable<E> findFirst(String where) {
        return null;
    }

    private String getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) {
            throw new ORMException("Provided class does not have Entity annotation.");
        }

        return annotation.name();
    }
}
