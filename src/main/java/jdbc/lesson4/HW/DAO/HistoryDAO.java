package jdbc.lesson4.HW.DAO;

import jdbc.lesson4.HW.model.History;
import jdbc.lesson4.HW.model.OperationType;
import jdbc.lesson4.HW.model.Status;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO extends DAO {

    public HistoryDAO() {
    }

    public History save(History history) throws IllegalArgumentException {
        validate(history);
        long idForMessage = history.getId();

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO History VALUES(?,?,?,?)")) {

            prepareStatement.setLong(1, history.getId());
            prepareStatement.setString(2, history.getOperationType().toString());
            prepareStatement.setLong(3, history.getTimeProcessed());
            prepareStatement.setString(4, history.getStatus().toString());


            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.out.println("HistorySave something wrong!" + " History id:" + idForMessage);
            e.printStackTrace();
        }
        return history;
    }//History

    public History delete(long id) throws Exception {
        History History = findById(id);
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM History WHERE ID=?")) {
            prepareStatement.setLong(1, id);

            int res = prepareStatement.executeUpdate();
            System.out.println("delete was finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong in Delete History method");
            e.printStackTrace();
        }
        return History;
    }//delete

    public void validate(History History) throws IllegalArgumentException {


    }

    public List<History> getHistorys() {
        List<History> Historys = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM History")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String operationType = resultSet.getString(2);
                    long timeProcessed = resultSet.getLong(3);
                    String status = resultSet.getString(4);

                    OperationType operType = OperationType.valueOf(operationType);
                    Status stat = Status.valueOf(status);

                    Historys.add(new History(id, operType, timeProcessed, stat));
                }
                return Historys;
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong in getHistorys");
            e.printStackTrace();
        }
        return null;
    }//getHistorys

    public History findById(long id) throws Exception {
        List<History> historys = new ArrayList<>();
        historys = getHistorys();
        for (History element : historys) {
            if (element != null) {
                if (element.getId() == id) {
                    return element;
                }
            }
        }
        return null;
    }


    public History update(History History) throws IllegalArgumentException {
        validate(History);
        long idForMessage = History.getId();

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("UPDATE History SET OPERATIONTYPE=?, TIMEPROCESSED=?, STATUS=? WHERE ID=?")) {


            prepareStatement.setString(1, History.getOperationType().toString());
            prepareStatement.setLong(2, History.getTimeProcessed());
            prepareStatement.setString(3, History.getStatus().toString());
            prepareStatement.setLong(4, History.getId());

            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.out.println("Update something wrong!" + " History id:" + idForMessage);
            e.printStackTrace();
        }
        return History;
    }//History

    public long freePlace() throws Exception{
        long i = 1;

        while (findById(i)!=null){
            i++;
        }
        return i;
    }

    public History add(OperationType operationType, long timeProcessed, Status status) throws Exception{
        long id  = freePlace();
        History history = new History(id, operationType, timeProcessed, status);
        save(history);
        return history;
    }
}
