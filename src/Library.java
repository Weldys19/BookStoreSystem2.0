import java.util.List;

public class Library {
    List<Book> books;
    List<Author> authors;
    List<String> weLend;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getWeLend() {
        return weLend;
    }

    public void setWeLend(List<String> weLend) {
        this.weLend = weLend;
    }
}
