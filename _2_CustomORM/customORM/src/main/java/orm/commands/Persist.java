package orm.commands;

import orm.records.KeyValuePairs;
import orm.ORMException;
import orm.annotations.MyColumn;
import orm.annotations.MyEntity;
import orm.annotations.MyId;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Persist<E> {

    private static final String USERNAME_COLUMN_MISSING_MESSAGE = "No username column present.";
    private static final String ENTITY_ANNOTATION_EXCEPTION = "Provided class does not have Entity annotation.";
    private static final String COLUMN_ANNOTATION_EXCEPTION = "Provided class does not have Column annotation.";
    
    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values (%s)";
    private static final String UPDATE_QUERY_FORMAT = "update %s e set %s where e.username = %s";
    private static final String UPDATE_VALUE_FORMAT = "%s = %s";

    //Todo - check Bankov and redo
    public String doPersist(E entity) throws IllegalAccessException {

        final Field idColumn = getIdColumn(entity.getClass());
        idColumn.setAccessible(true);

        final Field usernameColumn = getUsernameColumn(entity.getClass());
        usernameColumn.setAccessible(true);

        final Object usernameValue = usernameColumn.get(entity);

        if (usernameValue == null) {
            return doInsert(entity);
        }
        return doUpdate(entity, usernameColumn);
    }

    private String doUpdate(E entity, Field usernameColumn) throws IllegalAccessException {
        final String tableName = getTableName(entity.getClass());
        usernameColumn.setAccessible(true);

        final List<KeyValuePairs> keyValuePairs = getKeyValuePairs(entity);

        final String updateValues = keyValuePairs.stream()
                .map(keyValuePair -> String.format(UPDATE_VALUE_FORMAT, keyValuePair.key(), keyValuePair.value()))
                .collect(Collectors.joining(","));

        final String usernameValue = usernameColumn.get(entity).toString();

        return String.format(UPDATE_QUERY_FORMAT, tableName, updateValues, usernameValue);
    }

    private String doInsert(E entity) {

        String tableName = getTableName((Class<?>) entity);

        final List<KeyValuePairs> keyValuePairs = getKeyValuePairs(entity);

        final String fields = keyValuePairs.stream()
                .map(KeyValuePairs::key)
                .collect(Collectors.joining(","));

        final String values = keyValuePairs.stream()
                .map(KeyValuePairs::value)
                .collect(Collectors.joining(","));

        return String.format(INSERT_QUERY_FORMAT, tableName, fields, values);
    }

    private List<KeyValuePairs> getKeyValuePairs(E entity) {

            final Class<?> aClass = entity.getClass();

            return Arrays.stream(aClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(MyColumn.class))
                    .map(field ->
                    {
                        try {
                            return new KeyValuePairs(field.getAnnotationsByType(MyColumn.class)[0].name(),
                                   mapColumnsToGivenType(field, entity));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());

    }

    private String mapColumnsToGivenType(Field field, E entity) throws IllegalAccessException {
        field.setAccessible(true);

        Object o = null;

        try {
            o = field.get(entity);
        } catch (ORMException e) {
            throw new ORMException(COLUMN_ANNOTATION_EXCEPTION);
        }

        return o instanceof String || o instanceof LocalDate
                ? "'" + o + "'"
                : Objects.requireNonNull(o).toString();
    }


    /*private String getValuesOfFieldsWithoutId(E entity) throws IllegalAccessException {

        Field[] declaredFields = entity.getClass().getDeclaredFields();

        List<String> valueList = new ArrayList<>();


        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(MyColumn.class) != null) {
                declaredField.setAccessible(true);

                Object value = declaredField.get(entity);

                valueList.add("\"" + value.toString() + "\"");
            }
        }
        return valueList.toString();

    }

    private String getFieldsWithoutId(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(MyColumn.class) != null)
                .map(field -> field.getAnnotation(MyColumn.class).name())
                .collect(Collectors.joining(","));

    }*/
    private String getTableName(Class<?> entity) {
        MyEntity entityTableAnnotation = entity.getAnnotation(MyEntity.class);

        if (entityTableAnnotation == null) {
            throw new ORMException(ENTITY_ANNOTATION_EXCEPTION);
        }

        return entityTableAnnotation.name();
    }

    private Field getIdColumn(Class<?> aClass) {

        return Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MyId.class))
                .findFirst()
                .orElseThrow(() -> new ORMException(USERNAME_COLUMN_MISSING_MESSAGE));
    }


    private Field getUsernameColumn(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MyColumn.class))
                .filter(field -> field.getName().equals("username"))
                .findFirst()
                .orElseThrow(() -> new ORMException(USERNAME_COLUMN_MISSING_MESSAGE));
    }
}
