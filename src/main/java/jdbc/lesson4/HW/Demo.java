package jdbc.lesson4.HW;

import jdbc.lesson4.HW.DAO.FileDAO;
import jdbc.lesson4.HW.DAO.HistoryDAO;
import jdbc.lesson4.HW.DAO.StorageDAO;
import jdbc.lesson4.HW.model.File;
import jdbc.lesson4.HW.model.Storage;



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

        File file1 = new File(1, "MyFile1",  "doc", 34, storage);
        File file2 = new File(2, "MyFile2", "doc", 50, null);
        File file3 = new File(3, "MyFile3", "doc", 50, null);
        File file4 = new File(4, "MyFile4",  "doc", 34, storage);
        File file5 = new File(5, "MyFile5",  "doc", 44, storage);
        File file6 = new File(6, "MyFile6",  "doc", 84, storage);
        File file7 = new File(7, "MyFile7", "doc", 45, storage);

        //History history = new History(1, OperationType.PUT, 2, Status.SUCCESS);

        //storageDAO.update(storage2);
        //storageDAO.update(storage3);

        //historyDAO.save(history);

//        fileDAO.save(file1);
//        fileDAO.save(file4);
//        fileDAO.save(file5);
//        fileDAO.save(file6);
       // fileDAO.save(file7);
       // System.out.println(fileDAO.findById(1));
         //System.out.println(fileDAO.delete(1));

        //fileDAO.update(file);

       // storageDAO.save(storage);

        //controller.put(storage, file3);
     //controller.delete(storage3, file3);
        //System.out.println(controller.readFileList(storage));

        //controller.transferall(storage, storage3);

        controller.transferFile(storage3, storage, 7);

    }
}
