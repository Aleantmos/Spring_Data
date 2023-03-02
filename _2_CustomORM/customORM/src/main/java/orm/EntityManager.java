package orm;

import orm.annotations.MyColumn;
import orm.annotations.MyEntity;
import orm.annotations.MyId;
import orm.commands.Persist;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {

    private static final String INSERT_INTO = "insert into %s (%s) values (%s)";

    //update table(name) set column(name) = value(getTheValue)  where column(name) = value
    private static final String UPDATE = "update %s";
    private static final String UPDATE_VALUE_FORMAT = "%s = %s";

    private final Connection connection;
    private final Persist<E> persist;

    public EntityManager(Connection connection) {
        this.connection = connection;
        this.persist = new Persist<E>();
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {

        String queryToExecute = persist.doPersist(entity);

        return connection.prepareStatement(queryToExecute).execute();

    }

    private boolean insert(E entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);

        String fieldList = getDBFieldsWithoutIdentity(entity);

        String valueList = getValuesWithoutIdentity(entity);

        PreparedStatement statement = connection.prepareStatement(String.format(INSERT_INTO, tableName, fieldList, valueList));

        return statement.execute();
    }

    private Field getIdColumn(Class<?> aClass) {
        return Arrays.stream(aClass.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MyId.class))
                .findFirst()
                .orElseThrow(() ->
                        new ORMException("Entity does not exists or has not have id."));
    }

    @Override
    public boolean update(E entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);

        List<KeyValuePair> keyValuePairs = getKeyValuePairs(entity);

        List<String> valuesWithoutIdentity = getValuesForUpdate(entity);

        int entityId = getValueOfIdentity(entity);

        List<String> sets = new ArrayList<>();


        /*for (int i = 0; i < fields.size(); i++) {
            sets.add(fields.get(i) + " = " + valuesWithoutIdentity.get(i));
        }
        String SET = String.join(", ", sets);

        String WHERE = "where id = " + entityId;

        String UPDATE = "update " + tableName;

        String query = String.format(UPDATE + " set " + SET + " " + WHERE);

        PreparedStatement statement = connection.prepareStatement(query);

        return statement.execute();*/
        return true;
    }

    private List<KeyValuePair> getKeyValuePairs(E entity) {
        return null;
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
        MyEntity annotation = entity.getClass().getAnnotation(MyEntity.class);

        if (annotation == null) {
            throw new ORMException("Provided class does not have Entity annotation.");
        }

        return annotation.name();
    }

    private String getDBFieldsWithoutIdentity(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(MyColumn.class) != null)
                .map(field -> field.getAnnotation(MyColumn.class).name())
                .collect(Collectors.joining(","));


    }

    private List<String> getDBFieldsForUpdate(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(MyColumn.class) != null)
                .map(field -> field.getAnnotation(MyColumn.class).name())
                .collect(Collectors.toList());
    }

    private int getValueOfIdentity(E entity) throws IllegalAccessException {

         Field idColumn = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MyId.class))
                .findFirst()
                .orElseThrow(() ->
                        new ORMException("Entity does not exists or has not have id."));

        idColumn.setAccessible(true);

        Object idValue = idColumn.get(entity);

        int result = Integer.parseInt(idValue.toString()) ;

        return result;

    }

    private String getValuesWithoutIdentity(E entity) throws IllegalAccessException {
        List<String> result = getValues(entity);
        return String.join(",", result);
    }

    private List<String> getValuesForUpdate(E entity) throws IllegalAccessException {
        return getValues(entity);
    }

    private List<String> getValues(E entity) throws IllegalAccessException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        List<String> result = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(MyColumn.class) == null) {
                continue;
            }

            declaredField.setAccessible(true);

            Object value = declaredField.get(entity);

            result.add("\"" + value.toString() + "\"");

        }
        return result;
    }

    private record KeyValuePair(String key, String value) {}
}
