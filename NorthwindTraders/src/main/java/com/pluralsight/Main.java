package com.pluralsight;

import com.pluralsight.data.ProductDAO;
import com.pluralsight.model.Product;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        displayAllProductsScreen(dataSource);
        displayProductSearchScreen(dataSource);
    }

    private static void displayAllProductsScreen(BasicDataSource dataSource) {
        ProductDAO productDAO = new ProductDAO(dataSource);
        List<Product> products = productDAO.getAllProducts();

        System.out.printf("%-5s %-30s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
        System.out.println("---------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-5d %-30s $%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    product.getUnitsInStock());
        }
    }

    private static void displayProductSearchScreen(BasicDataSource dataSource) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Search for products that start with: ");
        String searchTerm = scanner.nextLine() + "%";

        ProductDAO productDAO = new ProductDAO(dataSource);
        List<Product> products = productDAO.searchProducts(searchTerm);

        System.out.println("\nSearch Results:");
        System.out.printf("%-5s %-30s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
        System.out.println("---------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("%-5d %-30s $%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    product.getUnitsInStock());
        }
    }
}