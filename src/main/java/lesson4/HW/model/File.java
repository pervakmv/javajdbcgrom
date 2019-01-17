package lesson4.HW.model;

import java.util.Objects;

public class File extends Entity {

    private String name;
    private String format;
    private long size;
    private Storage storage;


    public File(long id, String name, String format, long size, Storage storage) {
        super(id);
        this.name = name;
        this.format = format;
        this.size = size;
        this.storage = storage;
    }

    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public long getSize() {
        return size;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return super.getId() == file.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getId());
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", storage=" + storage +
                '}';
    }
}
