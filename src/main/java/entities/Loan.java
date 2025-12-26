package entities;

import java.time.LocalDateTime;

public class Loan {
    private int id;
    private Book book;
    private Client client;
    private LocalDateTime loanDate = LocalDateTime.now();
    private LocalDateTime returnDate;

    public Loan(Book book, Client client) {
        this.book = book;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book +
                ", client=" + client +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
