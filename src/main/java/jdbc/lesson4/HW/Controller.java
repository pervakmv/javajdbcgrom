package jdbc.lesson4.HW;

import jdbc.lesson4.HW.DAO.FileDAO;
import jdbc.lesson4.HW.DAO.HistoryDAO;
import jdbc.lesson4.HW.DAO.StorageDAO;
import jdbc.lesson4.HW.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Controller {
    FileDAO fileDAO = new FileDAO();

    StorageDAO storageDAO = new StorageDAO();

    public void put(Storage storage, File file) throws Exception {

        if (file.getStorage() != null) {
            if (file.getStorage().equals(storage))
                throw new Exception("File with id:" + file.getId() + " already in storage " + storage.getId());
            else {
                throw new Exception("File with id:" + file.getId() + " in storage with id :" + storage.getId());
            }
        }

        checkFormat(storage, file);
        StorageDAO storageDAO = new StorageDAO();
        storageDAO.storageExist(storage);
        fileDAO.fileExist(file);
        long fileId = file.getId();
        String name = file.getName();
        String format = file.getFormat();

        long size = file.getSize();
        if (size > storageDAO.freeSize(storage))
            throw new Exception("No free space in storage id: " + storage.getId() + "for file with id:" + fileId);

        File updatingFile = new File(fileId, name, format, size, storage);
        fileDAO.update(updatingFile);

    }

    public void delete(Storage storage, File file) throws Exception {
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

        Date startTime = new Date();
        List<File> files = fileDAO.readFileList(storageFrom);

        //Валидация переноса
        validateFilesForStorage(files, storageTo);

        try (Connection connection = FileDAO.getConnection()) {
            saveFileList(files, connection, storageTo);
        }

    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
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

            } catch (SQLException e) {
                connection.rollback();
                System.out.println(" Something wrong when transfer file with id: " + id + " from store with id:" + storageFrom.getId() + " to storage with id: " + storageTo.getId());
            }

        }

    }

}
