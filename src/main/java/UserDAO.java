import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO implements InterfaceDAO<User> {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR(100))");
        }
    }

    @Override
    public void deleteTable() throws SQLException {

    }

    @Override
    public void insert(User user) throws SQLException {
        final String sql = "INSERT INTO user (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.name);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<User> getById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void update(User ob) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String str = String.format("DELETE FROM user WHERE id= '%d'", id);
            statement.executeUpdate(str);
        }

    }
}
