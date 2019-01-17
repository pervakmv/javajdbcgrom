package lesson4.HW.model;

import java.util.Arrays;
import java.util.Objects;

public class Storage extends Entity {

    private String[] formatsSupported;
    private String storageCountry;
    private long storageMaxSize;

    public Storage() {

    }

    public Storage(long id, String[] formatsSupported, String storageCountry, long storageMaxSize) {
        super(id);
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
    }


    public long getId() {
        return super.getId();
    }

    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public long getStorageMaxSize() {
        return storageMaxSize;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + super.getId() +
                ", formatsSupported=" + Arrays.toString(formatsSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageMaxSize=" + storageMaxSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return super.getId() == storage.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getId());
    }

    public String formatSupportedToString(){
        if(formatsSupported==null)
            return null;
        String result = new String();
        for(int i=0;i<formatsSupported.length;i++){
            result += (formatsSupported[i]+",");
        }
        return result;
    }

    public String[]formatSupportedToStringArray(String inputStr){
        if(inputStr==null)
            return null;
        return inputStr.split(",");
    }



}//Storage
