import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class SqlLibraryRepository implements ILibraryRepository {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private final CommentDAO commentDAO;
    private final UserDAO userDAO;

    public SqlLibraryRepository(AuthorDAO authorDAO, BookDAO bookDAO, CommentDAO commentDAO, UserDAO userDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Collection<Author> getAllAuthors() {
        try {
            return authorDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch authors", e);
        }
    }

    @Override
    public Collection<Book> getAllBooks() throws SQLException {
        return bookDAO.getAll();
    }

    @Override
    public void saveBook(Book book, Author author) {
        try {
            if (author.id == 0) {
                authorDAO.insert(author);
                Collection<Author> authors = authorDAO.findByName(author.name);
                Author authorNext = authors.iterator().next();
                book.author_id = authorNext.id;
            }else{
                book.author_id = author.id;
            }
            bookDAO.insert(book);
        } catch (SQLException throwable) {
            throw new RuntimeException("Failed to save book",throwable);
        }
    }

    public void saveBook(Book book, Author author, Comment comment) {
        try {
            if (author.id == 0) {
                authorDAO.insert(author);
                Collection<Author> authors = authorDAO.findByName(author.name);
                Author authorNext = authors.iterator().next();
                book.author_id = authorNext.id;
            }else{
                book.author_id = author.id;
            }
            bookDAO.insert(book);
        } catch (SQLException throwable) {
            throw new RuntimeException("Failed to save book",throwable);
        }
    }

    @Override
    public void saveAuthor(Author author) {
        try {
            if (author.id == 0) {
                authorDAO.insert(author);
            } else {
                authorDAO.update(author);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save authors", e);
        }
    }

    @Override
    public void saveComment(Comment comment) {

    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void deleteAuthor(Author author) {

    }

    @Override
    public void deleteBook(Book book) {

    }

    @Override
    public Optional<Author> getAuthorById(int id) throws SQLException {
        return authorDAO.getById(id);
    }

    @Override
    public Optional<Book> getBookById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> getCommentById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Book> getBooksByAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteComment(Comment comment) {

    }

    @Override
    public void deleteUser(User user) {

    }
}
