package entities;

import util.Status;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book {
    private String name;
    private Author author;
    private int id;
    private Status status = Status.DISPONIVEL;
    private LocalDateTime registrationDate = LocalDateTime.now();

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", id=" + id +
                ", status=" + status +
                ", registrationDate=" + registrationDate +
                '}';
    }
}

