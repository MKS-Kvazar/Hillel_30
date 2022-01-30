import java.sql.*;
import java.util.Optional;

public class CommentDAO implements InterfaceDAO<Comment> {
    private final Connection connection;

    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS comment (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "text VARCHAR(100), user_id INTEGER)");
        }
    }

    @Override
    public void deleteTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS comment");
        }
    }

    @Override
    public void insert(Comment comment) throws SQLException {
        final String sql = "INSERT INTO comment (text, user_id) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, comment.text);
            statement.setInt(2, comment.user_id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Comment> getById(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(String.format("SELECT * FROM comment WHERE id = %d", id));
            if (cursor.next()) {
                return Optional.of(createCommentFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(Comment comment) throws SQLException {
        if (comment.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }
        final String sql = "UPDATE comment SET text = ?, user_id = ?, WHERE id =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, comment.text);
            statement.setInt(2, comment.user_id);
            statement.setInt(3, comment.id);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String str = String.format("DELETE FROM comment WHERE id= '%d'", id);
            statement.executeUpdate(str);
        }

    }
        private Comment createCommentFromCursorIfPossible(ResultSet cursor) throws SQLException {
            Comment comment = new Comment();
            comment.id = cursor.getInt("id");
            comment.text = cursor.getString("text");
            comment.user_id = cursor.getInt("user_id");
            return comment;
        }
}
