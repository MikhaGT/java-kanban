public class Task {

    protected String name;
    protected String description;
    private int id;
    protected Status status;

    Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = Status.NEW;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
