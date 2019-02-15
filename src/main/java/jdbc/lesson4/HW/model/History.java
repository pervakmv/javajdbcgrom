package jdbc.lesson4.HW.model;


import java.util.Objects;

public class History {
    private long id;
    private OperationType operationType;
    private long timeProcessed;
    private Status status;

    public History() {
    }

    public History(long id, OperationType operationType, long timeProcessed, Status status) {
        this.id = id;
        this.operationType = operationType;
        this.timeProcessed = timeProcessed;
        this.status = status;
    }


    public long getId() {
        return id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public long getTimeProcessed() {
        return timeProcessed;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", operationType=" + operationType +
                ", timeProcessed=" + timeProcessed +
                ", status=" + status +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return id == history.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
