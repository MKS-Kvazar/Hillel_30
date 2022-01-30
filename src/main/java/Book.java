public class Book {
    public int id;
    public String title;
    public int author_id;
    public String comment_id;  // Строка для обозначения нескольких id для множества отзывов

    public Book(String title, int author_id) {
        this.title = title;
        this.author_id = author_id;
    }
    public Book(String title) {
        this.title = title;
        this.author_id = author_id;
    }

    public Book(){
    }
}
