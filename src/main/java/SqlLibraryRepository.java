import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

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
            } else {
                book.author_id = author.id;
            }
            bookDAO.insert(book);
        } catch (SQLException throwable) {
            throw new RuntimeException("Failed to save book", throwable);
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
    public void saveComment(Comment comment, User user, Book book) {
        try {
            if (book.id == 0) {
                throw new RuntimeException("Failed to save comment, no such book");
            }
            if (user.id == 0) {
                userDAO.insert(user);
                Collection<User> users = userDAO.findByName(user.name);
                User userNext = users.iterator().next();
                comment.user_id = userNext.id;
            } else {
                comment.user_id = user.id;
            }
            commentDAO.insert(comment);
            Collection<Comment> comments = commentDAO.findByName(comment.text);
            Comment commentNext = comments.iterator().next();
            if (book.comment_id == null) {
                book.comment_id = String.format("%s", commentNext.id);
            } else {
                book.comment_id = String.format("%s %s", book.comment_id, commentNext.id);
            }
            bookDAO.update(book);
        } catch (SQLException throwable) {
            throw new RuntimeException("Failed to save comment", throwable);
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            if (user.id == 0) {
                userDAO.insert(user);
            } else {
                userDAO.update(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public void deleteAuthor(Author author) throws SQLException {
        authorDAO.delete(author.id);
    }

    @Override
    public void deleteBook(Book book) throws SQLException {
        bookDAO.delete(book.id);
    }

    @Override
    public Optional<Author> getAuthorById(int id) throws SQLException {
        return authorDAO.getById(id);
    }

    @Override
    public Optional<Book> getBookById(int id) throws SQLException {
        return bookDAO.getById(id);
    }

    @Override
    public Optional<Comment> getCommentById(int id) throws SQLException {
        return commentDAO.getById(id);
    }

    @Override
    public Optional<User> getUserById(int id) throws SQLException {
        return userDAO.getById(id);
    }

    @Override
    public Collection<Book> getBooksByAuthor(Author author) throws SQLException {
        return bookDAO.getBooksByAuthor(author);
    }

    @Override
    public Collection<Comment> getCommentsByBook(Book book) throws SQLException {
        Collection<Integer> id_comments = new ArrayList<>();
        Scanner scanner = new Scanner(book.comment_id);
        while (scanner.hasNextInt()) {
            id_comments.add(scanner.nextInt());
        }
        Collection<Comment> comments = new ArrayList<>();
        for (int x : id_comments) {
            comments.add(commentDAO.getById(x).get());
        }
        return comments;
    }

    @Override
    public void deleteComment(Comment comment) throws SQLException {
        commentDAO.delete(comment.id);
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        userDAO.delete(user.id);
    }

    @Override
    public void updateAuthor(Author author) throws SQLException {
        authorDAO.update(author);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        bookDAO.update(book);
    }

    @Override
    public void updateComment(Comment comment) throws SQLException {
        commentDAO.update(comment);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }
}
