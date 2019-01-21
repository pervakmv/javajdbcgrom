package lesson4.HW;

import lesson4.HW.DAO.FileDAO;
import lesson4.HW.DAO.HistoryDAO;
import lesson4.HW.DAO.StorageDAO;
import lesson4.HW.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
    public void put(Storage storage, File file) throws Exception {

        if (file.getStorage() != null) {
            if (file.getStorage().equals(storage))
                throw new Exception("File with id:" + file.getId() + " already in storage " + storage.getId());
            else {
                throw new Exception("File with id:" + file.getId() + " in storage with id :" + storage.getId());
            }
        }

        HistoryDAO historyDAO = new HistoryDAO();


        Date startTime = new Date();

        History history = historyDAO.add(OperationType.PUT, 0, Status.FAILED);

        checkFormat(storage, file);
        StorageDAO storageDAO = new StorageDAO();
        storageDAO.storageExist(storage);
        FileDAO fileDAO = new FileDAO();
        fileDAO.fileExist(file);


        long fileId = file.getId();
        String name = file.getName();
        String format = file.getFormat();

        long size = file.getSize();
        if (size > storageDAO.freeSize(storage))
            throw new Exception("No free space in storage id: " + storage.getId() + "for file with id:" + fileId);

        File updatingFile = new File(fileId, name, format, size, storage);
        fileDAO.update(updatingFile);
        Date finishTime = new Date();
        long timeProcessed = finishTime.getTime() - startTime.getTime();

        History historyFinish = new History(history.getId(), OperationType.PUT, timeProcessed, Status.SUCCESS);
        historyDAO.update(historyFinish);
    }

    public void delete(Storage storage, File file) throws Exception {
        HistoryDAO historyDAO = new HistoryDAO();
        History history = historyDAO.add(OperationType.DELETE, 0, Status.FAILED);
        Date startTime = new Date();
        FileDAO fileDAO = new FileDAO();
        File fileFromBase = fileDAO.findById(file.getId());

        if (fileFromBase == null) {
            throw new Exception("The file with id:" + file.getId() + " is not exist.");
        }

        if (!storage.equals(fileFromBase.getStorage())) {
            throw new Exception("The storage with id:" + storage.getId() + " does not have a file id:" + file.getId());
        }

        long fileId = file.getId();
        String name = file.getName();
        String format = file.getFormat();
        long size = file.getSize();

        File newFile = new File(fileId, name, format, size, null);
        fileDAO.update(newFile);
        Date finishTime = new Date();
        long timeProcessed = finishTime.getTime() - startTime.getTime();

        History historyFinish = new History(history.getId(), OperationType.DELETE, timeProcessed, Status.SUCCESS);
        historyDAO.update(historyFinish);
    }

    public void checkFormat(Storage inStorage, File inFile) throws Exception {
        boolean ok = false;
        for (String str : inStorage.getFormatsSupported()) {
            if (str.equals(inFile.getFormat())) {
                ok = true;
                break;
            }
        }
        if (ok != true)
            throw new Exception("Format of file with id: " + inFile.getId() + " not supported");
    }

    public List<File> readFileList(Storage storage) throws SQLException {
        List<File> files = new ArrayList<>();
        try (Connection connection = FileDAO.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "SELECT *FROM FILE_ WHERE STORAGE_ID=" + storage.getId();
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String format = resultSet.getString(3);
                    int size = resultSet.getInt(4);
                    files.add(new File(id, name, format, size, storage));
                }
            }
        } catch (SQLException e) {
            System.out.println("Something wrong in readFileList");
            throw e;
        }
        return files;
    }

    public void validateFilesForStorage(List<File> files, Storage storage) throws Exception {
        //Проверяем допусимость формата
        long size = 0;
        for (File file : files) {
            size += file.getSize();
            checkFormat(storage, file);
        }
        StorageDAO storageDAO = new StorageDAO();
        if (size > storageDAO.freeSize(storage))
            throw new Exception("No free space in storage id: " + storage.getId() + "for file transfer files");
    }

    public void validateFileForStorage(File file, Storage storage) throws Exception {

    }


    public void saveFileList(List<File> files, Connection connection, Storage storageTo) throws SQLException {
        long idForCatch = 0;
        try (PreparedStatement prepareStatement = connection.prepareStatement("UPDATE FILE_ SET NAME=?, FORMAT=?, FILESIZE=?, STORAGE_ID=? WHERE ID=?")) {
            connection.setAutoCommit(false);
            for (File file : files) {

                prepareStatement.setString(1, file.getName());
                prepareStatement.setString(2, file.getFormat());
                prepareStatement.setLong(3, file.getSize());
                prepareStatement.setLong(4, storageTo.getId());

                prepareStatement.setLong(5, file.getId());

                int res = prepareStatement.executeUpdate();
                System.out.println("Save was finished with result " + res);
                idForCatch = file.getId();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Something wrong when save lis of file to storage :" + storageTo.getId() + " File id: " + idForCatch);
            throw e;
        }
    }

    public void transferall(Storage storageFrom, Storage storageTo) throws Exception {
        HistoryDAO historyDAO = new HistoryDAO();
        History history = historyDAO.add(OperationType.TRANSFERALL, 0, Status.FAILED);
        Date startTime = new Date();
        List<File> files = readFileList(storageFrom);

        //Валидация переноса
        validateFilesForStorage(files, storageTo);

        try (Connection connection = FileDAO.getConnection()) {
            saveFileList(files, connection, storageTo);
        }
        Date finishTime = new Date();
        long timeProcessed = finishTime.getTime() - startTime.getTime();

        History historyFinish = new History(history.getId(), OperationType.TRANSFERALL, timeProcessed, Status.SUCCESS);
        historyDAO.update(historyFinish);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        HistoryDAO historyDAO = new HistoryDAO();
        History history = historyDAO.add(OperationType.TRANSFERFILE, 0, Status.FAILED);
        StorageDAO storageDAO = new StorageDAO();
        Date startTime = new Date();
        FileDAO fileDAO = new FileDAO();
        File file = fileDAO.findById(id);
        if (file == null)
            throw new Exception("File with id: " + file.getId() + " is not exist in the storage with id: " + storageFrom.getId());
        checkFormat(storageTo, file);
        if (file.getSize() > storageDAO.freeSize(storageTo))
            throw new Exception("No free space in storage id: " + storageTo.getId() + "for file transfer files");

        try (Connection connection = FileDAO.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement prepareStatement = connection.prepareStatement("UPDATE FILE_ SET NAME=?, FORMAT=?, FILESIZE=?, STORAGE_ID=? WHERE ID=?")) {

                prepareStatement.setString(1, file.getName());
                prepareStatement.setString(2, file.getFormat());
                prepareStatement.setLong(3, file.getSize());
                prepareStatement.setLong(4, storageTo.getId());
                prepareStatement.setLong(5, file.getId());

                int res = prepareStatement.executeUpdate();
                System.out.println("Save was finished with result " + res);

                connection.commit();

                Date finishTime = new Date();
                long timeProcessed = finishTime.getTime() - startTime.getTime();

                History historyFinish = new History(history.getId(), OperationType.TRANSFERFILE, timeProcessed, Status.SUCCESS);
                historyDAO.update(historyFinish);


            } catch (SQLException e) {
                connection.rollback();
                System.out.println(" Something wrong when transfer file with id: " + id + " from store with id:" + storageFrom.getId() + " to storage with id: " + storageTo.getId());
            }

        }

    }

}
