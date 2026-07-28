package org.example.dao;

import org.example.model.Book;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDAO {

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:library.db");
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "isbn TEXT, " +
                "available BOOLEAN NOT NULL)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books(title, author, isbn, available) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setBoolean(4, book.isAvailable());
            pstmt.executeUpdate();
            System.out.println("Added: " + book.getTitle());
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, isbn, available FROM books";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn")
                );
                book.setId(rs.getInt("id"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Error reading books: " + e.getMessage());
        }
        return books;
    }
    public List<Book> findByTitle(String searchTerm) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, isbn, available FROM books WHERE title LIKE ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn")
                );
                book.setId(rs.getInt("id"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Error searching books: " + e.getMessage());
        }
        return books;
    }
    public void updateAvailability(int id, boolean available) {
        String sql = "UPDATE books SET available = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book updated.");
            } else {
                System.out.println("No book found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }
}