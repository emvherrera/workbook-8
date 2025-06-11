package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Database credentials should ideally not be hardcoded or passed as command line arguments
        // in a production environment. For this example, we'll assume they are provided.
        String username = "root"; // Replace with your database username
        String password = "yearup"; // Replace with your database password

        // If running from command line, you can pass username and password as args:
        // java App your_username your_password
        if (args.length >= 2) {
            username = args[0];
            password = args[1];
        } else {
            System.out.println("Usage: java App <database_username> <database_password>");
            System.out.println("Using default placeholder credentials. Please update for actual use.");
        }


        Scanner scanner = new Scanner(System.in);
        System.out.print("Search for products that start with: ");
        String searchTerm = scanner.nextLine() + "%"; // Append '%' for LIKE operator to find names starting with the term

        // SQL query to select products where the product_name starts with the searchTerm.
        // Using a placeholder '?' for the search term to prevent SQL injection.
        // Assuming a 'products' table with 'product_id', 'product_name', and 'price' columns.
        String sql = """
                 SELECT product_id, product_name, price
                 FROM products
                 WHERE product_name LIKE ?;
                """;

        // Outer try-with-resources block for Connection and PreparedStatement.
        // This ensures these resources are automatically closed when the block exits,
        // whether normally or due to an exception.
        try (
                // Establish a connection to the MySQL database.
                // Replace "your_database_name" with the actual name of your database.
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", username, password);

                // Create a PreparedStatement object with the SQL query.
                // PreparedStatements are more efficient and secure for executing SQL queries
                // with dynamic parameters.
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Set the parameter for the PreparedStatement.
            // The first '?' in the SQL query will be replaced by the searchTerm.
            preparedStatement.setString(1, searchTerm);

            // Nested try-with-resources block for ResultSet.
            // This ensures the ResultSet is also automatically closed.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Print the header row for the output table.
                System.out.printf("%-4s %-40s %10s%n", "Id", "Product Name", "Price");
                System.out.println("_________________________________________________________________________________");

                // Declare variables outside the loop to ensure they are in scope
                // for the printf statement, even though they are reassigned inside.
                // This addresses potential unusual "cannot find symbol" errors related to scope.
                int id;
                String productName = ""; // Initialize with an empty string
                double price = 0.0;    // Initialize with a default value

                // Loop through each row in the ResultSet.
                // resultSet.next() moves the cursor to the next row and returns true if there is one, false otherwise.
                while (resultSet.next()) {
                    // Retrieve data from the current row by column name and reassign to variables.
                    id = resultSet.getInt("product_id");
                    productName = resultSet.getString("product_name");
                    price = resultSet.getDouble("price"); // Assuming 'price' is a double or decimal type

                    // Print the retrieved data in a formatted row.
                    System.out.printf("%-4d %-40s %10.2f%n", id, productName, price);
                }
            } // ResultSet is automatically closed here
        } catch (SQLException e) {
            // Catch block to handle any SQL-related exceptions.
            // Display a user-friendly error message.
            System.out.println("There was an error retrieving the products. Please try again or contact support.");
            // Print the stack trace for debugging purposes (for the developer).
            e.printStackTrace();
        } // Connection and PreparedStatement are automatically closed here
    }
}