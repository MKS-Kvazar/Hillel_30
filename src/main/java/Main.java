import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            new Main().run_1(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void run_1(Connection connection) throws SQLException {
        AuthorDAO authorDAO = new AuthorDAO(connection);
        BookDAO bookDAO = new BookDAO(connection);
        CommentDAO commentDAO = new CommentDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        authorDAO.createTable();
        bookDAO.createTable();
        commentDAO.createTable();
        userDAO.createTable();
        ILibraryRepository libraryRepository = new SqlLibraryRepository(authorDAO, bookDAO, commentDAO, userDAO);
        libraryRepository.saveBook(new Book("Учебник C++"), new Author("Страуструп", 1950));
        libraryRepository.saveBook(new Book("Учебник Java"), new Author("Шилдт", 1951));
        libraryRepository.saveBook(new Book("Дневник мага"), new Author("Коэ́льо", 1947));
        libraryRepository.saveBook(new Book("Властелин колец"), new Author("Толкин", 1892));
        libraryRepository.getAllBooks();
        for (Book ob : libraryRepository.getAllBooks()) {
            String str = String.format("%d  %s  Автор: %s",
                    ob.id, ob.title, libraryRepository.getAuthorById(ob.author_id).get().name);
            System.out.println(str);
        }
    }
}