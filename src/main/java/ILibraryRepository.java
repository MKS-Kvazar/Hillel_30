import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface ILibraryRepository {
    Collection<Author> getAllAuthors();

    Collection<Book> getAllBooks() throws SQLException;

    void saveBook(Book book, Author author);

    void saveAuthor(Author author);

    void saveComment(Comment comment);

    void saveUser(User user);

    void deleteAuthor(Author author);

    void deleteBook(Book book);

    Optional<Author> getAuthorById(int id) throws SQLException;

    Optional<Book> getBookById(int id);

    Optional<Comment> getCommentById(int id);

    Optional<User> getUserById(int id);

    Collection<Book> getBooksByAuthor(Author author);

    void deleteComment(Comment comment);

    void deleteUser(User user);

}
