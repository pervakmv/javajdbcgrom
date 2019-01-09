package lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public List<Product> findProductsByPrice(int price, int delta) {
        List<Product> resArrayList = new ArrayList<>();

        List<Product> products = ProductDAO.getProducts();

        for (Product element : products) {
            int priceOfProduct = element.getPrice();
            if ((priceOfProduct >= (price - delta)) && priceOfProduct <= (price + delta)) {
                resArrayList.add(element);
            }
        }
        return resArrayList;
    }

    public List<Product> findProductsByName(String word) throws Exception {

        validateWord(word);
        List<Product> resArrayList = new ArrayList<>();
        List<Product> products = ProductDAO.getProducts();
        for (Product element : products) {
            if (element.getName().equals(word)) {
                resArrayList.add(element);
            }
        }
        return resArrayList;
    }


    private void validateWord(String word) throws IllegalArgumentException {
        if (word.length() < 3)
            throw new IllegalArgumentException("number of characters less than three");
        String[] words = word.split(" ");
        if (words.length > 1)
            throw new IllegalArgumentException("the number of words is greater than one");
        char[] chrAr = word.toCharArray();
        for (char ch : chrAr) {
            if (!Character.isLetter(ch)
                    && !Character.isDigit(ch))
                throw new IllegalArgumentException("there are specials in the string");
        }
    }

    public List<Product> findProductsWithEmptyDescription() {
        List<Product> resArrayList = new ArrayList<>();
        List<Product> products = ProductDAO.getProducts();
        for (Product product : products) {

            if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
                resArrayList.add(product);

            }
        }
        return resArrayList;
    }


    void testSavePerformance() throws Exception {


        try (Connection connection = ProductDAO.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES(?, ?, ?)")) {
            for (int i = 0; i < 1000; i++) {
                prepareStatement.setLong(1, i);
                prepareStatement.setString(2, "test speed row" + i);
                prepareStatement.setInt(3, i % 8);

                int res = prepareStatement.executeUpdate();

            }
        } catch (SQLException e) {
            System.err.println("Something went wrong in savePerformance");
            e.printStackTrace();
        }


    }

    void testDeleteByIdPerformance() throws Exception {
        try (Connection connection = ProductDAO.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE ID=?");
            for (int i = 0; i < 1000; i++) {
                prepareStatement.setLong(1, i);

                int res = prepareStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong in deleteByIdPerformance");
        }
    }

    void testDeletePerformance() throws Exception {
        try (Connection connection = ProductDAO.getConnection()) {
            Statement statement = connection.createStatement();

            int res = statement.executeUpdate("DELETE FROM TEST_SPEED WHERE ID>=0 AND ID<=999");
        } catch (SQLException e) {
            System.err.println("Something went wrong in deleteByIdPerformance");
        }
    }


    private void outputResultSet(ResultSet inResultSet) throws SQLException {
        while (inResultSet.next()) {
            System.out.print(inResultSet.getLong(1));
            System.out.print(inResultSet.getString(2));
            System.out.println(inResultSet.getInt(3));

        }
    }

    public void testSelectPerformance() {
        try (Connection connection = ProductDAO.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM TEST_SPEED")) {
                outputResultSet(resultSet);
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong in testSelectPerformance");

        }


    }

    public void testSelectByIdPerformance() {
        try (Connection connection = ProductDAO.getConnection();
             Statement statement = connection.createStatement()) {

            for (long i = 0; i < 1000; i++) {
                String sql = "SELECT *FROM TEST_SPEED WHERE ID=" + i;
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    outputResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in testSelectByIdPerformenc");
        }
    }


}
