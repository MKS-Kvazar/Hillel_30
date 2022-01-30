import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AuthorDAO implements InterfaceDAO <Author> {
    private final Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS author (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR(100), birth_year INTEGER)");
        }
    }

    public void deleteTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS author");
        }
    }

    public void update(Author author) throws SQLException {
        if (author.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE author SET name = ?, birth_year = ?, WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.name);
            statement.setInt(2, author.birthYear);
            statement.setInt(3, author.id);
            statement.executeUpdate();
        }
    }

    public Collection<Author> findByName(String text) throws SQLException {
        Collection<Author> authors = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String str = String.format("SELECT * FROM author WHERE name LIKE '%%%s%%'",text);
            ResultSet cursor = statement.executeQuery(str);
            while (cursor.next()){
                authors.add(createAuthorFromCursorIfPossible(cursor));
            }
        }
        return authors;
    }

    public void insert(Author author) throws SQLException {
        final String sql = "INSERT INTO author (name, birth_year) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.name);
            statement.setInt(2, author.birthYear);
            statement.executeUpdate();
        }
    }

    public Collection<Author> getAll() throws SQLException {
        Collection<Author> authors = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM author");
            while (cursor.next()) {
                authors.add(createAuthorFromCursorIfPossible(cursor));
            }
        }
        return authors;
    }

    public Optional<Author> getById(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(String.format("SELECT * FROM author WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createAuthorFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String str = String.format("DELETE FROM author WHERE id= '%d'", id);
            statement.executeUpdate(str);
        }
    }

    private Author createAuthorFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Author author = new Author();
        author.id = cursor.getInt("id");
        author.name = cursor.getString("name");
        author.birthYear = cursor.getInt("birth_year");
        return author;
    }
}