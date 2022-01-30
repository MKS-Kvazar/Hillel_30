import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface ILibraryRepository {
    Collection<Author> getAllAuthors();

    Collection<Book> getAllBooks() throws SQLException;

    void saveBook(Book book, Author author);

    void saveAuthor(Author author);

    void saveComment(Comment comment, User user, Book book);

    void saveUser(User user);

    void deleteAuthor(Author author) throws SQLException;

    void deleteBook(Book book) throws SQLException;

    Optional<Author> getAuthorById(int id) throws SQLException;

    Optional<Book> getBookById(int id) throws SQLException;

    Optional<Comment> getCommentById(int id) throws SQLException;

    Optional<User> getUserById(int id) throws SQLException;

    Collection<Book> getBooksByAuthor(Author author) throws SQLException;

    Collection<Comment> getCommentsByBook(Book book) throws SQLException;

    void deleteComment(Comment comment) throws SQLException;

    void deleteUser(User user) throws SQLException;

    void updateAuthor(Author author) throws SQLException;

    void updateBook(Book book) throws SQLException;

    void updateComment(Comment comment) throws SQLException;

    void updateUser(User user) throws SQLException;
}
