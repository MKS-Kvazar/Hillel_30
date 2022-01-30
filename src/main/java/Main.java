import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

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
        Book book_1 = libraryRepository.getBookById(1).get();
        Book book_2 = libraryRepository.getBookById(2).get();
        Book book_3 = libraryRepository.getBookById(3).get();
        Book book_4 = libraryRepository.getBookById(4).get();

        libraryRepository.saveComment(new Comment("Отличная книга"), new User("Tom"), book_1);
        libraryRepository.saveComment(new Comment("Легко усваивается материал"), new User("Антон"), book_2);
        libraryRepository.saveComment(new Comment("Хорошее качественное изложение"), new User("Света"), book_3);
        libraryRepository.saveComment(new Comment("Захватывающий сюжет"), new User("Ivan"), book_4);
        libraryRepository.saveComment(new Comment("Замечательная книга, супер!"), new User("Виктория"), book_4);

        for (Book ob : libraryRepository.getAllBooks()) {
            String str = String.format("%d  %s  Автор: %s",
                    ob.id, ob.title, libraryRepository.getAuthorById(ob.author_id).get().name);
            System.out.println(str);
            System.out.println("Комментарии: ");
            if (ob.comment_id != null) {
                Scanner scanner = new Scanner(ob.comment_id);
                while (scanner.hasNext()) {
                    Comment comment = libraryRepository.getCommentById(scanner.nextInt()).get();
                    System.out.println(libraryRepository.getUserById(comment.user_id).get().name
                            + ": " + comment.text);
                }
            }
        }
    }
}