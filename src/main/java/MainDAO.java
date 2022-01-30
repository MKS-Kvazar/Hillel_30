import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class MainDAO {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            new MainDAO().taskSQL(connection);
//            new MainDAO().deleteAll(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void deleteAll(Connection connection) throws SQLException{
        AuthorDAO authorDAO = new AuthorDAO(connection);
        authorDAO.deleteTable();
    }

    void taskSQL(Connection connection) throws SQLException {
        AuthorDAO authorDAO = new AuthorDAO(connection);
        authorDAO.createTable();
        Author author_1 = new Author("Страуструп", 1950);
        Author author_2 = new Author("Шилдт", 1951);
        Author author_3 = new Author("Коэ́льо", 1947);
        Author author_4 = new Author("Толкин", 1892);
        authorDAO.insert(author_1);
        authorDAO.insert(author_2);
        authorDAO.insert(author_3);
        authorDAO.insert(author_4);
        Collection<Author> authors = authorDAO.getAll();
        for(Author x:authors){
            System.out.println(x.id + " " + x.name + " " + x.birthYear);
        }
        System.out.println();
        Optional<Author> optional = authorDAO.getById(2);
        System.out.println(optional.get().id + " " + optional.get().name + " " + optional.get().birthYear);
        authorDAO.delete(4);
        System.out.println();
        Collection<Author> authors2 = authorDAO.getAll();
        for(Author x:authors2){
            System.out.println(x.id + " " + x.name + " " + x.birthYear);
        }
        System.out.println("Find");
        Collection<Author> authors3 = authorDAO.findByName("т");
        for(Author x:authors3){
            System.out.println(x.id + " " + x.name + " " + x.birthYear);
        }
    }
}
