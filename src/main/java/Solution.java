import java.sql.*;
import java.util.ArrayList;

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

                    if (description.length() > 50) {
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

    public ArrayList<Product> getAllProductsToArrayList() {
        ArrayList<Product> resArrayList = new ArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM PRODUCT2")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    resArrayList.add(new Product(id, productName, description, price));

                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong in getAllProductsToArrayList");
            e.printStackTrace();
        }
        return resArrayList;
    }

    public ArrayList<Product> add100toPrice(ArrayList<Product> inputArrayList) {
        if (inputArrayList == null)
            return null;

        ArrayList<Product> resArrayList = new ArrayList<>();
        for (Product element : inputArrayList) {
            if (element.getPrice() < 100) {
                int price = element.getPrice();
                price += 100;
                resArrayList.add(new Product(element.getId(), element.getName(), element.getDescription(), price));
            }
        }
        return resArrayList;
    }


    public ArrayList<Product> updateData(ArrayList<Product> inputArrayList) {
        if (inputArrayList == null)
            return null;

        ArrayList<Product> resArrayList = new ArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            for (Product element : inputArrayList) {
                int price = element.getPrice();
                long id = element.getId();
                String sql = "UPDATE PRODUCT2 SET PRICE = " + price + " WHERE ID =" + id;
                int response = statement.executeUpdate(sql);
                if (response > 0) {
                    resArrayList.add(element);
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong in updateData");
            e.printStackTrace();
        }
        return resArrayList;
    }

    public String subSentences(String inputStr) {
        if (inputStr == null)
            return null;

        String[] stringArray = inputStr.split("[.\\?\\!]");
        String outputString = new String();
        for (int i = 0; i < stringArray.length - 1; i++) {
            outputString += stringArray[i] + ".";
        }
        return outputString;
    }

    public ArrayList<Product> withoutExtraAplication(ArrayList<Product> inputArrayList) {
        if (inputArrayList == null)
            return null;
        ArrayList<Product> resArrayList = new ArrayList<>();
        for (Product element : inputArrayList) {
            String description = element.getDescription();
            if (description.length() >= 100) {
                description = subSentences(description);
                resArrayList.add(new Product(element.getId(), element.getName(),description, element.getPrice()));
            }
        }
        return resArrayList;
    }


    void increasePrice() {
        System.out.println(updateData(add100toPrice(getAllProductsToArrayList())));
    }

    void changeDescription(){
        System.out.println(updateData(withoutExtraAplication(getAllProductsToArrayList())));
    }

}

