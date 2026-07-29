package org.example;

import org.example.dao.BookDAO;
import org.example.model.Book;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.createTable();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Library Menu =====");
            System.out.println("1. Add a book");
            System.out.println("2. List all books");
            System.out.println("3. Search by title");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    bookDAO.addBook(new Book(title, author, isbn));
                    break;

                case "2":
                    System.out.println("\nAll books:");
                    for (Book book : bookDAO.getAllBooks()) {
                        System.out.println(book);
                    }
                    break;

                case "3":
                    System.out.print("Enter search term: ");
                    String term = scanner.nextLine();
                    System.out.println("\nResults:");
                    List<Book> results = bookDAO.findByTitle(term);
                    if (results.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        for (Book book : results) {
                            System.out.println(book);
                        }
                    }
                    break;

                case "4":
                    System.out.print("Enter book ID to borrow: ");
                    int borrowId = Integer.parseInt(scanner.nextLine());
                    bookDAO.updateAvailability(borrowId, false);
                    break;

                case "5":
                    System.out.print("Enter book ID to return: ");
                    int returnId = Integer.parseInt(scanner.nextLine());
                    bookDAO.updateAvailability(returnId, true);
                    break;

                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}