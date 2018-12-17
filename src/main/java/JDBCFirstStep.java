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


            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM TEST")) {
                System.out.println("Start");
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                System.out.println("columnsNumber = " + columnsNumber); //правильно определяет количество колонок
             //   System.out.println(resultSet.getString(1));

                while (resultSet.next()) {
                    //TODO do something
                    System.out.println("Object found");
                }
            }
        }  catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}

