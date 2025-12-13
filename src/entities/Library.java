package entities;

import util.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<String> weLend = new ArrayList<>();

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

    public Book chooseBook(int id) {
        for (Book book : this.books) {
            if (book.getId() == id) {
                if (book.getStatus().equals(Status.INDISPONIVEL)) {
                    throw new LibraryException("Livro indisponivel");
                }
                return book;
            }
        }
        throw new LibraryException("Nenhum livro encontrado com esse ID");
    }


    public void lendBook(String client, int id){
            Book chosenBook = chooseBook(id);
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String date = currentDate.format(fmt);
            chosenBook.setStatus(Status.INDISPONIVEL);
            String loanSummary = String.format("\nLivro emprestado: %s\nCliente: %s\nData: %s\n", chosenBook.toString(), client, date);
            weLend.add(loanSummary);
    }

    public String returnLoan(List<String> weLend){
        return weLend.getLast();
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
