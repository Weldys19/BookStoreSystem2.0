package application;

import entities.Book;
import entities.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        library.insertData();

        String userResponse;

        do {
            System.out.println("Deseja ver os livros da nossa biblioteca? ");
            userResponse = sc.next();

            if (userResponse.equalsIgnoreCase("SIM")){
                System.out.println("\nLivros disponiveis\n");
                for (Book book : library.getBooksAvailable(library.getBooks())){
                    System.out.println(book.toString());
                }
            }
        } while (!userResponse.equalsIgnoreCase("NAO"));

        System.out.println("Sistema encerrado");

        sc.close();
    }
}
