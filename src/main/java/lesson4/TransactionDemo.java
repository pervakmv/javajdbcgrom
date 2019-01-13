package lesson4;

import lesson3.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDemo {
    //1. save order  - pay money - receive money
    //2. save order - pay money - recive moeny


    //CRUD - create, read, update, delete
    private static final String DB_URL = "jdbc:oracle:thin:@pervakmv-gromcode-lessons.ckeuefrwg72q.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "FastovFox2278";

    public static void main(String[] args) {
        Product product1 = new Product(55, "!!!", "!!!", 777);
        Product product2 = new Product(66, "!!!", "!!!", 777);
        Product product3 = new Product(77, "!!!", "!!!", 777);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        save(products);
    }


    public static void save(List<Product> products) {

        try (Connection connection = getConnection()){
            saveList(products, connection);
        } catch (SQLException e) {

            System.err.println("Something went wrong in updateData");
            e.printStackTrace();
        }

    }

    private static void saveList(List<Product> products, Connection connection) throws SQLException{
        long idForCatch = 0;
        try (PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO PRODUCT2 VALUES(?, ?, ?, ?)")) {

            connection.setAutoCommit(false);
           
            for (Product product : products) {

                prepareStatement.setLong(1, product.getId());
                prepareStatement.setString(2, product.getName());
                prepareStatement.setString(3, product.getDescription());
                prepareStatement.setInt(4, product.getPrice());
                int res = prepareStatement.executeUpdate();
                System.out.println("save was finished with result " + res);
                idForCatch = product.getId();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            System.out.println(idForCatch);
           throw e; //Для того щоб перекинути вище Лог
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
