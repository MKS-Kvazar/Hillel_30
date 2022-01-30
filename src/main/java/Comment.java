public class Comment {
    public int id;
    public String text;
    public int user_id;

    public Comment(String text, int user_id) {
        this.text = text;
        this.user_id = user_id;
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment() {
    }
}
