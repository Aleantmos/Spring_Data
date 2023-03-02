package orm;

import java.sql.SQLException;

public interface DbContext<E> {

    boolean persist(E entity) throws SQLException, IllegalAccessException;

    boolean update(E entity) throws IllegalAccessException, SQLException;

    Iterable<E> find();

    Iterable<E> find(String where);

    Iterable<E> findFirst();

    Iterable<E> findFirst(String where);
}
