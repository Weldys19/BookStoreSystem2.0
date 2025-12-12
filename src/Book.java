public class Book {
    String name;
    Author author;
    int id;
    Status status = Status.DISPONIVEL;

    public Book(String name, Author author, int id) {
        this.name = name;
        this.author = author;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author=" + author.getName() +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
