package lesson4.HW.DAO;

import lesson4.HW.model.File;
import lesson4.HW.model.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.sql.Types.NULL;

public class StorageDAO extends DAO {

    public StorageDAO() {
    }

    public Storage save(Storage Storage) throws IllegalArgumentException {
        validate(Storage);
        long idForMessage = Storage.getId();

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO Storage VALUES(?,?,?,?)")) {


            prepareStatement.setLong(1, Storage.getId());
            prepareStatement.setString(2, Storage.formatSupportedToString());
            prepareStatement.setString(3, Storage.getStorageCountry());
            prepareStatement.setLong(4, Storage.getStorageMaxSize());


            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.out.println("StorageSave something wrong!" + " Storage id:" + idForMessage);
            e.printStackTrace();
        }
        return Storage;
    }//Storage

    public Storage delete(long id) throws Exception {
        Storage Storage = findById(id);
        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM Storage_ WHERE ID=?")) {
            prepareStatement.setLong(1, id);

            int res = prepareStatement.executeUpdate();
            System.out.println("delete was finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong in Delete Storage method");
            e.printStackTrace();
        }
        return Storage;
    }//delete

    public void validate(Storage Storage) throws IllegalArgumentException {

        //long id = Storage.getId();

//        if (Storage.getName().length() > 10) {
//            throw new IllegalArgumentException("Storagename greater than 10 characters id:" + id);
//        }
    }

    public List<Storage> getStorages() {
        List<Storage> Storages = new ArrayList<>();
        Storage storage = new Storage();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT *FROM Storage")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String supportedFormatInStirng = resultSet.getString(2);
                    String storageCountry = resultSet.getString(3);
                    long storageMaxSize = resultSet.getLong(4);
                    String[] supportedFormat = storage.formatSupportedToStringArray(supportedFormatInStirng);
                    Storages.add(new Storage(id, supportedFormat, storageCountry, storageMaxSize));
                }
                return Storages;
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong in getStorages");
            e.printStackTrace();
        }
        return null;
    }//getStorages

    public Storage findById(long id) {
        List<Storage> Storages = new ArrayList<>();
        Storages = getStorages();
        for (Storage element : Storages) {
            if (element != null) {
                if (element.getId() == id) {
                    return element;
                }
            }
        }
        return null;
    }


    public Storage update(Storage storage) throws IllegalArgumentException {
        validate(storage);
        long idForMessage = storage.getId();

        try (Connection connection = getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Storage SET FORMATSUPPORTED=?, STORAGECOUNTRY=?, STORAGEMAXSIZE=? WHERE ID=?")) {


            prepareStatement.setString(1, storage.formatSupportedToString());
            prepareStatement.setString(2, storage.getStorageCountry());
            prepareStatement.setLong(3, storage.getStorageMaxSize());

            prepareStatement.setLong(4, storage.getId());

            int res = prepareStatement.executeUpdate();
            System.out.println("save was finished with result " + res);

        } catch (SQLException e) {
            System.out.println("Update something wrong!" + " Storage id:" + idForMessage);
            e.printStackTrace();
        }
        return storage;
    }

    public void storageExist(Storage storage) throws Exception {
        Storage readStorage = findById(storage.getId());
        if (readStorage == null)
            throw new Exception("Storage with id: " + storage.getId() + " is not exist");

    }

    public long freeSize(Storage storage) {//Определение своободного места
        FileDAO fileDAO = new FileDAO();

        List<File> files = fileDAO.getFiles();
        long sum = 0;
        for (File element : files) {
            if (element.getStorage() != null) {
                if (element.getStorage().equals(storage)) {
                    sum += element.getSize();
                }
            }

        }
        long freeSize = storage.getStorageMaxSize() - sum;
        return freeSize > 0 ? freeSize : 0;

    }




}//DAO storage
