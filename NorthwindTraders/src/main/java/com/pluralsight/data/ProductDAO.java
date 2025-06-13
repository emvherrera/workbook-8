package com.pluralsight.data;

import com.pluralsight.model.Product;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private BasicDataSource dataSource;

    public ProductDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAllProducts() {
        String sql = """
                SELECT * FROM products;
                """;
        List<Product> products = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ProductId");
                    String name = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("UnitPrice");
                    int stock = resultSet.getInt("UnitsInStock");

                    Product product = new Product(id, name, price, stock);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products. Please try again or contact support.");
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> searchProducts(String searchTerm) {
        String sql = """
                SELECT * FROM products
                WHERE ProductName LIKE ?;
                """;
        List<Product> products = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, searchTerm);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ProductId");
                    String name = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("UnitPrice");
                    int stock = resultSet.getInt("UnitsInStock");

                    Product product = new Product(id, name, price, stock);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching products. Please try again or contact support.");
            e.printStackTrace();
        }

        return products;
    }
}