package application;

import entities.Book;
import entities.Library;
import entities.LibraryException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        library.insertData();

        String userResponse = "";

        do {
            try {
                System.out.println("Deseja ver os livros da nossa biblioteca? (SIM|NAO)");
                userResponse = sc.next();
                sc.nextLine();

                if (userResponse.equalsIgnoreCase("SIM")) {
                    System.out.println("\nLivros disponiveis\n");
                    for (Book book : library.getBooksAvailable(library.getBooks())) {
                        System.out.println(book.toString());
                    }

                    System.out.println("\nEscolha atraves do ID o livro desejado: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    library.chooseBook(id);

                    System.out.println("Digite seu nome completo: ");
                    String client = sc.nextLine().toUpperCase();
                    library.lendBook(client, id);

                    System.out.printf("\nEmprestimo de livro efetuado com sucesso %s\n", library.returnLoan(library.getWeLend()));
                } else if (!userResponse.equalsIgnoreCase("NAO")) {
                    throw new LibraryException("A resposta deve ser 'SIM' ou 'NAO'");
                }
            } catch (LibraryException e){
                System.err.println(e.getMessage());

            } catch (InputMismatchException e){
                System.err.println("Esse campo so permite valores numericos inteiros");
                sc.nextLine();
            }

        } while (!userResponse.equalsIgnoreCase("NAO"));

        System.out.println("Sistema encerrado");

        sc.close();
    }
}
