import java.util.ArrayList;
import java.util.List;

public class Library {
    List<Book> books = new ArrayList<>();
    List<Author> authors = new ArrayList<>();
    List<String> weLend = new ArrayList<>();

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

    public List<Book> getBooksAvailable(List<Book> books) {
        List<Book> bookAvailable = new ArrayList<>();
        for (Book book : books) {
            if (book.getStatus().equals(Status.DISPONIVEL)) {
                bookAvailable.add(book);
            }
        }
        return bookAvailable;
    }

    public void insertData(){
        Author author = new Author("George Orwell", 1);
        authors.add(author);

        books.add(new Book("1984",author, 1));
        books.add(new Book("A Revolução dos Bichos", author, 2));

        author = new Author("J. R. R. Tolkien", 2);
        authors.add(author);

        books.add(new Book("O Hobbit", author, 3));
        books.add(new Book("O Senhor dos Anéis: A Sociedade do Anel", author, 4));

        author = new Author("Jane Austen", 3);
        authors.add(author);

        books.add(new Book("Orgulho e Preconceito", author, 5));
        books.add(new Book("Razão e Sensibilidade", author, 6));
    }
}
