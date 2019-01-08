package lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    //CRUD - create, read, update, delete
    private static final String DB_URL = "jdbc:oracle:thin:@pervakmv-gromcode-lessons.ckeuefrwg72q.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "FastovFox2278";

    public Product save(Product product) {

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO PRODUCT2 VALUES(?, ?, ?, ?)")) {

            prepareStatement.setLong(1, product.getId());
            prepareStatement.setString(2, product.getName());
            prepareStatement.setString(3, product.getDescription());
            prepareStatement.setInt(4, product.getPrice());


            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.err.println("Something went wrong in updateData");
            e.printStackTrace();
        }
        return product;
    }

    public Product update(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("UPDATE  PRODUCT2 SET NAME = ?, DESCRIPTION=?, PRICE=? WHERE ID=?")) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setString(2, product.getDescription());
            prepareStatement.setInt(3, product.getPrice());
            prepareStatement.setLong(4, product.getId());


            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.err.println("Something went wrong in updateData");
            e.printStackTrace();
        }
        return product;

    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM PRODUCT2")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    products.add(new Product(id, productName, description, price));

                }
                return products;
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong in getAllProductsToArrayList");
            e.printStackTrace();
        }
        return null;
    }

    public Product getProductById(long id) throws Exception {
        List<Product> products = new ArrayList<>();
        products = getProducts();
        for (Product element : products) {

            if (element != null) {
                if (element.getId() == id) {
                    return element;
                }
            }
        }
        throw new Exception ("Product is not exist");
    }


    public Product delete(long id) throws Exception{
        Product product = getProductById(id);
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("DELETE  FROM PRODUCT2 WHERE ID=?")) {
            prepareStatement.setLong(1, id);
            int res = prepareStatement.executeUpdate();
            System.out.println("delete was finished with result " + res);

        } catch (SQLException e) {
            System.err.println("Something went wrong in updateData");
            e.printStackTrace();
        }
        return product;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
