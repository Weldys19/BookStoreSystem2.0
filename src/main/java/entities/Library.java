package entities;

import db.LibraryDAO;
import util.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private LibraryDAO dao = new LibraryDAO();

    public void addBook(String bookName, String nameAuthor, LocalDate authorDateOfBirth) {
        Author author = new Author(nameAuthor, authorDateOfBirth);
        Author authorExists = dao.authorAlreadyExists(author);
        if (authorExists != null){
            Book book = new Book(bookName, authorExists);
            dao.addBookDAO(book);
        } else {
            dao.addAuthorDAO(author);
            Book book = new Book(bookName, author);
            dao.addBookDAO(book);
        }
    }

    public List<Book> booksAvailable(){
        List<Book> booksAvailable = new ArrayList<>();
        List<Book> listBooks = dao.listBooksDAO();
        if (!listBooks.isEmpty()){
            for (Book book : listBooks) {
                if (book.getStatus().equals(Status.DISPONIVEL)) {
                    booksAvailable.add(book);
                }
            }
            if (booksAvailable.isEmpty()) {
                throw new LibraryException("Nenhum livro disponivel");
            }
        } else {
            throw new LibraryException("O sistema ainda nao possui livros cadastrados");
        }
        return booksAvailable;
    }

    public Book searchForBookById(int id) {
        List<Book> listBooks = dao.listBooksDAO();
        if (!listBooks.isEmpty()) {
            for (Book book : listBooks) {
                if (book.getId() == id) {
                    return book;
                }
            }
            throw new LibraryException("Nenhum livro encontrado com esse id");

        }
        throw new LibraryException("O sistema ainda nao possui livros cadastrados");
    }

    public List<Loan> loanList(){
        return dao.listLoanDAO();
    }

    public List<Loan> listAClientsLoan(Client client){
        List<Loan> listAClientsLoan = new ArrayList<>();

        List<Loan> listLoan = dao.listLoanDAO();
        for (Loan loan : listLoan){
            if (client.getName().equalsIgnoreCase(loan.getClient().getName()) &&
            client.getEmail().equalsIgnoreCase(loan.getClient().getEmail())){
                listAClientsLoan.add(loan);
            }
        }
        return listAClientsLoan;
    }

    public void addLoan(Client clientLoan, int borrowedBookId){
        List<Book> listBooks = dao.listBooksDAO();
        Book bookLoan = null;
        Client clientExists = dao.clientAlreadyExists(clientLoan);
        for (Book book : listBooks){
            if (book.getId() == borrowedBookId){
                if (book.getStatus().equals(Status.DISPONIVEL)){
                    bookLoan = book;
                } else {
                    throw new LibraryException("Livro nao esta disponivel para emprestimo");
                }
            }
        }
        if (bookLoan == null){
            throw new LibraryException("Esse livro nao esta na base de dados. Escolha um id valido");
        }

        if (clientExists == null) {
            dao.addClientDAO(clientLoan);
            Loan loan = new Loan(bookLoan, clientLoan);
            dao.addLoanDAO(loan);
        } else {
            Loan loan = new Loan(bookLoan, clientExists);
            dao.addLoanDAO(loan);
        }
    }

    public void returnBook(int id){
        Loan loanFound = dao.searchForLoanUsingIdDAO(id);
        if (loanFound != null){
            if (loanFound.getBook().getStatus().equals(Status.INDISPONIVEL)) {
                dao.returnBookDAO(loanFound);
            } else {
                throw new LibraryException("Esse livro já foi devolvido");
            }
        } else {
            throw new LibraryException("Emprestimo nao encontrado. Escolha um id valido");
        }
    }
}

