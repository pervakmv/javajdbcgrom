package lesson1and2;

import java.sql.*;

public class JDBCFirstStep {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@pervakmv-gromcode-lessons.ckeuefrwg72q.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "FastovFox2278";


    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {


            //1. DB Driver
            //2. create connection открытие этого самого моста, который обеспечен и проложен нашим драйвером
            //3. create query
            //4. execute query
            //5. work with result
            //6. close all the connection
            try { //регистрация драйвера
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                return;
            }


            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM PRODUCT2")) {
            //try(ResultSet resultSet = statement.executeQuery("SELECT *FROM ORDERS1")){
                while (resultSet.next()) {

                   // System.out.println("Object found");
                   // System.out.println(resultSet.getInt(1));
                    //System.out.println(resultSet.getString(2));
                    //Заповнюємо поля об'єкту даними з бази
                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);
                    //Date dateOrdered = resultSet.getDate(4);
                    //Date dateConfirmed = resultSet.getDate(5);


                    //Order order = new Order(id, productName, price, dateOrdered, dateConfirmed);
                    Product product = new Product(id, productName, description, price);
                    System.out.println(product);
                }
            }
        }  catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}

