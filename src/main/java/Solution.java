import java.sql.*;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@pervakmv-gromcode-lessons.ckeuefrwg72q.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "FastovFox2278";


    public Solution() {
    }

    public void saveProduct() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("INSERT INTO PRODUCT2 VALUES(999, 'toy', 'for children', 60)");
            System.out.println(response);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void deleteProducts() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("DELETE FROM PRODUCT2 WHERE NAME = 'toy'");
            System.out.println(response);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void deleteProductsByPrice() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("DELETE FROM PRODUCT2 WHERE PRICE < 100");
            System.out.println(response);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void getAllProducts() {


        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {


            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM PRODUCT2")) {
                while (resultSet.next()) {


                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);


                    Product product = new Product(id, productName, description, price);
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    public void getProductsByPrice() {


        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {


            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM PRODUCT2 WHERE PRICE <=100")) {
                while (resultSet.next()) {


                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);


                    Product product = new Product(id, productName, description, price);
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void getProductsByDescription() {


        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {


            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM PRODUCT2")) {
                while (resultSet.next()) {


                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    if(description.length()>50) {
                        Product product = new Product(id, productName, description, price);
                        System.out.println(product);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


}

