import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subTasks;

    public Epic(String name, String description){
        super(name, description);
        subTasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(Subtask subtask) {
        subTasks.add(subtask);
    }
}
