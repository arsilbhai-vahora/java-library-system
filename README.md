# Library Management System

A command-line library management application built in Java, using JDBC and SQLite for persistent data storage.

## Features

- Add books to the library
- List all books in the collection
- Search for books by title (partial matching)
- Data persists between sessions using a SQLite database

## Technologies Used

- **Java 21**
- **JDBC** for database connectivity
- **SQLite** for data storage
- **Maven** for build and dependency management

## Project Structure

The project follows a layered architecture:

- `model/` — the `Book` class, representing a book entity
- `dao/` — `BookDAO`, the Data Access Object handling all database operations
- `Main.java` — the entry point and interactive menu

## How to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA (or any Maven-compatible IDE)
3. Let Maven download the dependencies (SQLite JDBC driver)
4. Run `Main.java`
5. Use the menu to add, list, and search for books

## What I Learned

This project demonstrates object-oriented design, database integration using
prepared statements (preventing SQL injection), collections, exception handling,
and separation of concerns through a layered architecture.
