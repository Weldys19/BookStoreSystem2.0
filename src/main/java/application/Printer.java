package application;

import entities.Book;
import entities.Client;
import entities.Library;
import entities.Loan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class Printer {
    private Library library;
    private Scanner sc;

    public Printer(Library library, Scanner sc) {
        this.library = library;
        this.sc = sc;
    }

    public String featuresMenu(){
        return "\n1 - Lista de livros disponiveis  " +
                "5 - Listar emprestimos de um cliente   \n" +
                "2 - Buscar livro pelo id       " +
                "  6 - Devolver livro   \n" +
                "3 - Adicionar livro   " +
                "           7 - Solicitar emprestimo de um livro\n" +
                "4 - Lista de emprestimos   ";
    }

    public int printerUserResponse(){
        System.out.print("\nEscolha uma das funcionalidades atraves do numero (0 ENCERRA): ");
        int response = sc.nextInt();
        sc.nextLine();
        return response;
    }

    public String printerNameBook(){
        System.out.print("\nNome do livro: ");
        String name = sc.nextLine();
        return name.toUpperCase();
    }

    public String printerNameAuthor() {
        System.out.print("Nome do autor: ");
        String name = sc.nextLine();
        return name.toUpperCase();
    }

    public LocalDate printerDateOfBirth(){
        System.out.print("Data de nascimento do autor (DD/MM/yyyy): ");
        String dateOfBirth = sc.nextLine();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateOfBirth, fmt);
    }

    public void printerBooksAvailable(){
        if (!library.booksAvailable().isEmpty()) {
            for (Book book : library.booksAvailable()) {
                System.out.println(book.toString());
            }
        } else {
            System.out.println("\nNenhum livro disponivel");
        }
        System.out.print("\nPressione enter para continuar");
        sc.nextLine();
    }
    public void printerSearchForBookById(){
        System.out.print("\nDigite o id do livro que deseja buscar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.printf("\nLivro encontrado %s\n", library.searchForBookById(id));
        System.out.print("\nPressione enter para continuar");
        sc.nextLine();
    }
    public void printerLoanList(){
        if (!library.loanList().isEmpty()){
            for (Loan loan : library.loanList()){
                System.out.println(loan.toString());
            }
        } else {
            System.out.println("\nNenhum emprestimo registrado");
        }
        System.out.print("\nPressione enter para continuar");
        sc.nextLine();
    }

    public String printerClientName(){
        System.out.print("\nDigite o nome do cliente: ");
        String name = sc.nextLine();
        return name.toUpperCase();
    }

    public String printerClientEmail(){
        System.out.print("Digite o email do cliente (example@gmail.com): ");
        String name = sc.nextLine();
        return name.toUpperCase();
    }

    public void printerListAClientsLoan(Client client) {
        if (!library.listAClientsLoan(client).isEmpty()){
            for (Loan loan : library.listAClientsLoan(client)){
                System.out.println(loan.toString());
            }
        } else {
            System.out.println("\nNao tem registros desse cliente na base de dados");
        }
        System.out.print("\nPressione enter para continuar");
        sc.nextLine();
    }

    public void printerAddLoan(Client clientLoan){
        System.out.print("Digite o id do livro escolhido: ");
        int borrowedBookId = sc.nextInt();

        library.addLoan(clientLoan, borrowedBookId);
        System.out.println("\nEmprestimo realizado com sucesso");
        System.out.print("\nPressione enter para continuar");
        sc.nextLine();
    }

    public void printerReturnBook(){
        System.out.print("Digite o id do emprestimo: ");
        int loanId = sc.nextInt();
        sc.nextLine();

        library.returnBook(loanId);
        System.out.println("\nLivro devolvido com sucesso");
        System.out.print("\nPressione enter para continuar");
        sc.nextLine();
    }
}
