import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
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
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        }
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
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(String.format("SELECT * FROM user WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createUserFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        if (user.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE user SET name = ?, WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.name);
            statement.setInt(2, user.id);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String str = String.format("DELETE FROM user WHERE id= '%d'", id);
            statement.executeUpdate(str);
        }
    }

    private User createUserFromCursorIfPossible(ResultSet cursor) throws SQLException {
        User user = new User();
        user.id = cursor.getInt("id");
        user.name = cursor.getString("name");
        return user;
    }

    public Collection<User> findByName(String text) throws SQLException {
        Collection<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String str = String.format("SELECT * FROM user WHERE name LIKE '%%%s%%'", text);
            ResultSet cursor = statement.executeQuery(str);
            while (cursor.next()) {
                users.add(createUserFromCursorIfPossible(cursor));
            }
        }
        return users;
    }
}
