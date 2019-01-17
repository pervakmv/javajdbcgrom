package lesson4.HW.DAO;

import lesson4.HW.model.File;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class FileDAO extends DAO {

    public FileDAO() {
    }

    public File save(File file) throws IllegalArgumentException {
        validate(file);
        long idForMessage = file.getId();

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO FILE_ VALUES(?,?,?,?,?)")) {


            prepareStatement.setLong(1, file.getId());
            prepareStatement.setString(2, file.getName());
            prepareStatement.setString(3, file.getFormat());
            prepareStatement.setLong(4, file.getSize());
            if (file.getStorage() != null)
                prepareStatement.setLong(5, file.getStorage().getId());
            else
                prepareStatement.setNull(5, NULL);

            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.out.println("FileSave something wrong!" + " File id:" + idForMessage);
            e.printStackTrace();
        }
        return file;
    }//file

    public File delete(long id) throws Exception {
        File file = findById(id);
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM FILE_ WHERE ID=?")) {
            prepareStatement.setLong(1, id);

            int res = prepareStatement.executeUpdate();
            System.out.println("delete was finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong in Delete file method");
            e.printStackTrace();
        }
        return file;
    }//delete

    public void validate(File file) throws IllegalArgumentException {

        long id = file.getId();

        if (file.getName().length() > 10) {
            throw new IllegalArgumentException("Filename greater than 10 characters id:" + id);
        }
    }

    public List<File> getFiles() {
        List<File> files = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM FILE_")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String format = resultSet.getString(3);
                    long size = resultSet.getLong(4);
                    long storageId = resultSet.getLong(5);

                    StorageDAO storageDAO = new StorageDAO();
                    files.add(new File(id, name, format, size, storageDAO.findById(storageId)));
                }
                return files;
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong in getFiles");
            e.printStackTrace();
        }
        return null;
    }//getFiles

    public File findById(long id) throws Exception {
        List<File> files = new ArrayList<>();
        files = getFiles();
        for (File element : files) {
            if (element != null) {
                if (element.getId() == id) {
                    return element;
                }
            }
        }
        return null;
    }


    public File update(File file) throws IllegalArgumentException {
        validate(file);
        long idForMessage = file.getId();

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("UPDATE FILE_ SET NAME=?, FORMAT=?, FILESIZE=?, STORAGE_ID=? WHERE ID=?")) {


            prepareStatement.setString(1, file.getName());
            prepareStatement.setString(2, file.getFormat());
            prepareStatement.setLong(3, file.getSize());

            if (file.getStorage() != null)
                prepareStatement.setLong(4, file.getStorage().getId());
            else
                prepareStatement.setNull(4, NULL);

            prepareStatement.setLong(5, file.getId());

            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.out.println("Update something wrong!" + " File id:" + idForMessage);
            e.printStackTrace();
        }
        return file;
    }//file

    public void fileExist(File file) throws Exception {
        File readFile = findById(file.getId());
        if (readFile == null)
            throw new Exception("File with id: " + file.getId() + " is not exist");

    }

}//FileDAO
