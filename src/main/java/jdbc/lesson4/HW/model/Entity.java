package jdbc.lesson4.HW.model;

public abstract class Entity {
    private long id;

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}//Entity
