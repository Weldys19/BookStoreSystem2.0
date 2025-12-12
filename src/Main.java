import java.util.List;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        library.insertData();

        for (Book book : library.getBooks()){
            System.out.println(book.toString());
        }
    }
}
