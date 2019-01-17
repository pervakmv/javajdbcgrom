package lesson4.HW;

import lesson4.HW.DAO.FileDAO;
import lesson4.HW.DAO.HistoryDAO;
import lesson4.HW.DAO.StorageDAO;
import lesson4.HW.model.File;
import lesson4.HW.model.History;
import lesson4.HW.model.OperationType;
import lesson4.HW.model.Status;
import lesson4.HW.model.Storage;



public class Demo {



    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();

        FileDAO fileDAO = new FileDAO();
        StorageDAO storageDAO = new StorageDAO();
        HistoryDAO historyDAO = new HistoryDAO();

        String[] formatSupported = {"bmp", "txt", "doc"};
        String[] formatSupported2 = {"txt", "bat", "com"};
        String[] formatSupported3 = {"txt", "exc", "doc"};
        Storage storage = new Storage(1, formatSupported, "Ukraine", 700);
        Storage storage2 = new Storage(2, formatSupported2, "Slovenia", 1000);
        Storage storage3 = new Storage(3, formatSupported3, "Poland", 500);

        File file3 = new File(3, "MyFile3", "doc", 50, null);
        File file2 = new File(2, "MyFile2", "doc", 50, null);

        //History history = new History(1, OperationType.PUT, 2, Status.SUCCESS);

        //storageDAO.update(storage2);
        //storageDAO.update(storage3);

        //historyDAO.save(history);

        //fileDAO.update(file);
       // System.out.println(fileDAO.findById(1));
         //System.out.println(fileDAO.delete(1));

        //fileDAO.update(file);

       // storageDAO.save(storage);

        controller.put(storage3, file2);

    }
}
