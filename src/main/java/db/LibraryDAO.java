package db;

import entities.Author;
import entities.Book;
import entities.Client;
import entities.Loan;
import util.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {
    private DataBaseConnection connection;

    public LibraryDAO() {
        connection = new DataBaseConnection();
        connection.connect();
    }

    public void addBookDAO(Book book){
        String sql = "INSERT INTO books (name, author_id, status, registration_date) VALUES (?, ?, ?, ?)";
        Connection conn = connection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getName());
            stmt.setInt(2, book.getAuthor().getId());
            stmt.setString(3, book.getStatus().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(book.getRegistrationDate()));

            stmt.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    }

    public void addAuthorDAO(Author author){
        String sql = "INSERT INTO authors (name, date_of_birth) VALUES (?, ?)";
        Connection conn = connection.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, author.getName());
            stmt.setDate(2, Date.valueOf(author.getDateOfBirth()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                author.setId(rs.getInt(1));
            }

        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    }

    public Author authorAlreadyExists(Author author){
        String sql = "SELECT id, name, date_of_birth FROM authors";
        Connection conn = connection.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                if (author.getName().equalsIgnoreCase(rs.getString("name")) &&
                    author.getDateOfBirth().isEqual(rs.getDate("date_of_birth").toLocalDate())){
                    author.setId(rs.getInt("id"));
                    return author;
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
        return null;
    }

    public List<Book> listBooksDAO(){

        List<Book> listBooks = new ArrayList<>();

        String sql = """
                SELECT b.id, b.name, b.status, b.registration_date, a.id AS author_id,
                a.name AS author_name, a.date_of_birth AS author_date_of_birth FROM books b JOIN authors a ON b.author_id = a.id
                """;
        Connection conn = connection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Author author = new Author(rs.getString("author_name"),
                        rs.getDate("author_date_of_birth").toLocalDate());
                author.setId(rs.getInt("author_id"));

                Book book = new Book(rs.getString("name"), author);
                book.setStatus(Status.valueOf(rs.getString("status")));
                book.setId(rs.getInt("id"));
                book.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());

                listBooks.add(book);
            }
        } catch (SQLException e){
           throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    return listBooks;
    }

    public List<Loan> listLoanDAO(){

        List<Loan> listLoan = new ArrayList<>();

        String sql = """
                SELECT l.id, l.loan_date, c.id AS client_id, c.name AS client_name, c.email AS client_email,
                    b.id AS book_id, b.name AS book_name, b.status AS book_status, b.registration_date AS book_registration_date,
                    a.id AS author_id, a.name AS author_name, a.date_of_birth AS author_date_of_birth
                FROM loans l
                JOIN books b ON l.book_id = b.id
                JOIN clients c ON l.client_id = c.id
                JOIN authors a ON b.author_id = a.id
                """;

        Connection conn = connection.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Author author = new Author(rs.getString("author_name"),
                        rs.getDate("author_date_of_birth").toLocalDate());
                author.setId(rs.getInt("author_id"));

                Book book = new Book(rs.getString("book_name"), author);
                book.setStatus(Status.valueOf(rs.getString("book_status")));
                book.setId(rs.getInt("book_id"));
                book.setRegistrationDate(rs.getTimestamp("book_registration_date").toLocalDateTime());

                Client client = new Client(rs.getString("client_name"), rs.getString("client_email"));
                client.setId(rs.getInt("client_id"));
                Loan loan = new Loan(book, client);
                loan.setLoanDate(rs.getTimestamp("loan_date").toLocalDateTime());
                loan.setId(rs.getInt("id"));
                listLoan.add(loan);
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
        return listLoan;
    }

    public void addLoanDAO(Loan loan){
        String sql = """
                INSERT INTO loans (client_id, book_id, loan_date) VALUES (?, ?, ?)
                """;
        String sql1 = """
        UPDATE books SET status = ? WHERE id = ?
        """;
        Connection conn = connection.getConnection();
        try {
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, loan.getClient().getId());
            stmt.setInt(2, loan.getBook().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(loan.getLoanDate()));

            stmt.executeUpdate();

            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, Status.INDISPONIVEL.toString());
            stmt1.setInt(2, loan.getBook().getId());
            loan.getBook().setStatus(Status.INDISPONIVEL);

            stmt1.executeUpdate();

            conn.commit();

        } catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException ex){
                throw  new RuntimeException("Erro ao realizar o rollback", e);
            }
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    }
    public void addClientDAO(Client client){
        String sql = """
                INSERT INTO clients (name, email) VALUES (?, ?)
                """;
        Connection conn = connection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            while (rs.next()){
                client.setId(rs.getInt("id"));
            }

        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    }

    public Client clientAlreadyExists(Client client){
        String sql = "SELECT id, name, email FROM clients";
        Connection conn = connection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                if (client.getName().equalsIgnoreCase(rs.getString("name")) &&
                client.getEmail().equalsIgnoreCase(rs.getString("email"))){
                    client.setId(rs.getInt("id"));
                    return client;
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
        return null;
    }

    public Loan searchForLoanUsingIdDAO(int id){
        String sql = """
                SELECT l.id, l.loan_date, c.id AS client_id, c.name AS client_name, c.email AS client_email,
                b.id AS book_id, b.name AS book_name, b.status AS book_status, b.registration_date AS book_registration_date,
                a.id AS author_id, a.name AS author_name, a.date_of_birth AS author_date_of_birth
                FROM loans l
                JOIN books b ON l.book_id = b.id
                JOIN clients c ON l.client_id = c.id
                JOIN authors a ON b.author_id = a.id
                """;
        Connection conn = connection.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                if (rs.getInt("id") == id) {
                    Author author = new Author(rs.getString("author_name"),
                            rs.getDate("author_date_of_birth").toLocalDate());
                    author.setId(rs.getInt("author_id"));

                    Book book = new Book(rs.getString("book_name"), author);
                    book.setStatus(Status.valueOf(rs.getString("book_status")));
                    book.setId(rs.getInt("book_id"));
                    book.setRegistrationDate(rs.getTimestamp("book_registration_date").toLocalDateTime());

                    Client client = new Client(rs.getString("client_name"), rs.getString("client_email"));
                    client.setId(rs.getInt("client_id"));
                    Loan loan = new Loan(book, client);
                    loan.setLoanDate(rs.getTimestamp("loan_date").toLocalDateTime());
                    loan.setId(rs.getInt("id"));
                    return loan;
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
        return null;
    }

    public void returnBookDAO(Loan loan) {
        String sql = "UPDATE loans SET return_date = ? WHERE id = ?";
        String sql1 = "UPDATE books SET status = ? WHERE id = ?";

        Connection conn = connection.getConnection();

        try {
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, loan.getId());

            stmt.executeUpdate();

            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, Status.DISPONIVEL.toString());
            stmt1.setInt(2, loan.getBook().getId());

            stmt1.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao realizar o rollback", e);
            }
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }
    }
}

