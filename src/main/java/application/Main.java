package application;

import entities.Client;
import entities.Library;
import entities.LibraryException;

import java.time.LocalDate;
import java.util.InputMismatchException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        Printer printer = new Printer(library, sc);

        int userResponse = 1;

        do {
            System.out.println(printer.featuresMenu());

            try {
                userResponse = printer.printerUserResponse();

                switch (userResponse) {
                    case 0:
                        System.out.println("\nSistema encerrado");
                        break;
                    case 1:
                        printer.printerBooksAvailable();
                        break;
                    case 2:
                        printer.printerSearchForBookById();
                        break;
                    case 3:
                        String nameBook = printer.printerNameBook();
                        String nameAuthor = printer.printerNameAuthor();
                        LocalDate dateOfBirth = printer.printerDateOfBirth();
                        library.addBook(nameBook, nameAuthor, dateOfBirth);
                        break;
                    case 4:
                        printer.printerLoanList();
                        break;
                    case 5:
                        String clientName = printer.printerClientName();
                        String clientEmail = printer.printerClientEmail();
                        Client client = new Client(clientName, clientEmail);
                        printer.printerListAClientsLoan(client);
                        break;
                    case 6:
                        printer.printerReturnBook();
                        break;
                    case 7:
                        String clientNameLoan = printer.printerClientName();
                        String clientEmailLoan = printer.printerClientEmail();
                        Client clientLoan = new Client(clientNameLoan, clientEmailLoan);
                        printer.printerAddLoan(clientLoan);
                        break;
                    default:
                        System.out.print("\nA escolha deve ser de 1 a 7. Pressione enter");
                        sc.nextLine();
                }
            } catch (LibraryException e){
                System.err.println(e.getMessage() + "\n");
            } catch (InputMismatchException e){
                System.err.println("\nEsse campo so permite numeros inteiros.Pressione enter");
                sc.nextLine();
                sc.nextLine();
            }
        } while (userResponse != 0);
    }
}
