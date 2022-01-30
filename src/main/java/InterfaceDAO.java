import java.sql.SQLException;
import java.util.Optional;

public interface InterfaceDAO<T> {
    void createTable() throws SQLException;

    void deleteTable() throws SQLException;

    //реализациея CRUD операций.
    void insert(T ob) throws SQLException;

    Optional<T> getById(int id) throws SQLException;

    void update(T ob) throws SQLException;

    void delete(int id) throws SQLException;
}
