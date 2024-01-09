package com.example.customorm2.orm;


import com.example.customorm2.annotations.Column;
import com.example.customorm2.annotations.Entity;
import com.example.customorm2.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class EntityManager<E> implements DbContext<E> {

    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field id = this.getId((Class<E>) entity.getClass());
        id.setAccessible(true);

        Object value = id.get(entity.getClass());

        if (value == null) {
            return this.doInsert(entity, id);
        }

        return this.doUpdate(entity, id);
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private Field getId(Class<E> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have Id!"));
    }

    private boolean doInsert(E entity, Field primary) throws IllegalAccessException, SQLException {
        String query = "insert into " + this.getTableName((Class<E>) entity.getClass()) + " ";

        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");

        Field[] fields = entity.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Id.class)) {
                columns.append("`").append(this.getColumnName(field)).append("`,");

                Object value = field.get(entity);

                if (value instanceof Date) {
                    values.append("'").append(new SimpleDateFormat("yyyy-MM-dd").format(value)).append("'");
                } else if (value instanceof Integer) {
                    values.append(value);
                } else {
                    values.append("'").append(values).append("'");
                }

                if (i < fields.length - 1) {
                    values.append(",");
                    columns.append(",");
                }
            }
        }

        query += columns.toString() + " values " + values.toString() + ")";

        return connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, Field primary) throws IllegalAccessException, SQLException {
        String query = "update " + this.getTableName((Class<E>) entity.getClass()) + " set ";
        StringBuilder columnAnaValue = new StringBuilder();
        StringBuilder where = new StringBuilder();

        Field[] fields =  entity.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            Object value = field.get(entity);
            if (field.isAnnotationPresent(Id.class)) {
                where.append(" where ").append(this.getColumnName(field)).append(" = ").append(value);
            } else {
                if (value instanceof Date) {
                    columnAnaValue.append(this.getColumnName(field))
                            .append(" = '")
                            .append(new SimpleDateFormat("yyyy-MM-dd").format(value)).append("'");
                } else if (value instanceof Integer) {
                    columnAnaValue.append(this.getColumnName(field)).append(" = ").append(value);
                } else {
                    columnAnaValue.append(this.getColumnName(field)).append(" = '").append(value).append("'");
                }
                if (i < fields.length - 1) {
                    columnAnaValue.append(",");
                }
            }
        }
        query += columnAnaValue.toString() + where.toString();
        return connection.prepareStatement(query).execute();
    }

    private String getTableName(Class<E> entity) {
        String tableName = "";

        tableName = entity.getAnnotation(Entity.class).name();

        if (tableName == null || tableName.equals("")) {
            tableName = entity.getSimpleName();
        }
        return tableName;
    }

    private String getColumnName(Field field) {
        String columnName = "";

        columnName = field.getAnnotation(Column.class).name();

        if (columnName == null || columnName.equals("")) {
            columnName = field.getName();
        }

        return columnName;
    }
}
