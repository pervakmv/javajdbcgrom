package lesson4.HW;

import lesson4.HW.DAO.FileDAO;
import lesson4.HW.DAO.HistoryDAO;
import lesson4.HW.DAO.StorageDAO;
import lesson4.HW.model.*;

import java.util.Date;

public class Controller {
    public void put(Storage storage, File file) throws Exception{

        if(file.getStorage()!=null) {
            if (file.getStorage().equals(storage))
                throw new Exception("File with id:" + file.getId() + " already in storage " + storage.getId());
            else{
                throw new Exception("File with id:" + file.getId() + " in storage with id :" + storage.getId());
            }
        }

        HistoryDAO historyDAO = new HistoryDAO();



        Date startTime = new Date();

        History history = historyDAO.add(OperationType.PUT, 0, Status.FAILED);

        checkFormat(storage, file);
        StorageDAO storageDAO =  new StorageDAO();
        storageDAO.storageExist(storage);
        FileDAO fileDAO = new FileDAO();
        fileDAO.fileExist(file);


        long fileId = file.getId();
        String name = file.getName();
        String format = file.getFormat();

        long size = file.getSize();
        if(size > storageDAO.freeSize(storage))
            throw new Exception("No free space in storage id: " + storage.getId() + "for file with id:" + fileId);

        File updatingFile = new File(fileId, name, format, size, storage);
        fileDAO.update(updatingFile);
        Date finishTime = new Date();
        long timeProcessed = finishTime.getTime() - startTime.getTime();

        History historyFinish = new History(history.getId(), OperationType.PUT, timeProcessed, Status.SUCCESS);
        historyDAO.update(historyFinish);
    }


    public void checkFormat(Storage inStorage, File inFile) throws Exception{
        boolean ok = false;
        for(String str : inStorage.getFormatsSupported()){
            if(str.equals(inFile.getFormat())){
                ok = true;
                break;
            }
        }
        if(ok!=true)
            throw new Exception("Format of file with id: " + inFile.getId() + " not supported");
    }

}
