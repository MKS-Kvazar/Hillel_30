import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BookDAO implements InterfaceDAO<Book> {
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS book (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title VARCHAR(100), author_id INTEGER, comment_id VARCHAR(100))");
        }
    }

    public void deleteTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS book");
        }
    }

    public void update(Book book) throws SQLException {
        if (book.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE book SET title = ?, author_id = ?, comment_id = ? WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.author_id);
            statement.setString(3, book.comment_id);
            statement.setInt(4, book.id);
            statement.executeUpdate();
        }
    }

    public Collection<Book> findByName(String text) throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String str = String.format("SELECT * FROM book WHERE title LIKE '%%%s%%'", text);
            ResultSet cursor = statement.executeQuery(str);
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public void insert(Book book) throws SQLException {
        final String sql = "INSERT INTO book (title, author_id) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.author_id);
            statement.executeUpdate();
        }
    }

    public Collection<Book> getAll() throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM book");
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public Optional<Book> getById(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(String.format("SELECT * FROM book WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createBookFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String str = String.format("DELETE FROM book WHERE id= '%d'", id);
            statement.executeUpdate(str);
        }
    }

    private Book createBookFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Book book = new Book();
        book.id = cursor.getInt("id");
        book.title = cursor.getString("title");
        book.author_id = cursor.getInt("author_id");
        return book;
    }
}
