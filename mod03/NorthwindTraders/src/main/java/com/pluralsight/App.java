package com.pluralsight;

import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {
        String username = args[0];
        String password = args[1];

        //create a connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind",username, password);
        System.out.println(connection);

        //create a SQL statement/query
        String sql = """
                     select
                         productid,
                         productname,
                         unitprice,
                         unitsinstock
                     from products;
                    """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //execute the statement/query
        ResultSet resultSet =  preparedStatement.executeQuery();

        //print header row
        System.out.printf("%-4s %-40s %15s %10s%n", "Id", "Product Name", "Price", "Stock");
        System.out.println("_________________________________________________________________________________");

        //loop through the results and display them
        while (resultSet.next()){
            int productId = resultSet.getInt("productid");
            String productName = resultSet.getString("productname");
            Double unitPrice = resultSet.getDouble("unitprice");
            int unitsInStock = resultSet.getInt("unitsinstock");

            //print row
            System.out.printf("%-4d %-40s %15.2f %10d%n", productId, productName, unitPrice, unitsInStock);
        }

    }
}