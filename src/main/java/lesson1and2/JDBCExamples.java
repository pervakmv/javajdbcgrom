package lesson1and2;

import java.sql.*;

public class JDBCExamples {


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
//            try { //регистрация драйвера         //Драйвер Java запам"ятала з першого уроку тому тут його викликати не потрыбно
//                Class.forName(JDBC_DRIVER);
//            } catch (ClassNotFoundException e) {
//                return;
//            }

            //Тут boolean вертається ТАК тільки якщо були отримані хоч якісь дані з бази даних. Тобто якщо ми добавляємо щось в базу то ми отримаємо
            //FALSE
            //boolean res = statement.execute("INSERT INTO PRODUCT2(ID, NAME, DESCRIPTION, PRICE) VALUES(1, 'toy', 'for children', 60)");
            //boolean res = statement.execute("INSERT INTO PRODUCT2 VALUES(2, 'toy111', 'for children', 70)");

//            boolean res = statement.execute("DELETE FROM PRODUCT2 WHERE NAME = 'toy111'");
//            System.out.println(res);

            //response возвращает количество затронутых запросом строк таблицы
           // int response = statement.executeUpdate("INSERT INTO PRODUCT2 VALUES(5, 'car', 'for children', 770)");
             int response = statement.executeUpdate("DELETE FROM PRODUCT2 WHERE NAME = 'car'");
            System.out.println(response);

        }  catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
