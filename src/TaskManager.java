import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, Epic> epics;
    public HashMap<Integer, Subtask> subTasks;
    public int currentId;

    public TaskManager(){
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        currentId = 1;
    }

    public int generateId(){
        return currentId++;
    }

    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
    }

    public void createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
    }

    public void createSubTask(Subtask subTask, Epic epic) {
        int id = generateId();
        subTask.setId(id);
        subTask.setEpicId(epic.getId());
        epic.addSubTask(subTask);
        subTasks.put(id, subTask);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        deleteAllSubTasks();
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubTask(int id) {
        return subTasks.get(id);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeEpic(int id) {
        epics.remove(id);
        for (Subtask subtask : epics.get(id).getSubTasks()) {
            epics.get(id).getSubTasks().remove(subtask);
        }
    }

    public void removeSubTask(int id) {
        subTasks.remove(id);
    }

    public void updateTask(Task task, int id, Status status) {
        tasks.put(id, task);
        tasks.get(id).setStatus(status);
    }

    public void updateSubTask(Subtask subtask, int id, Status status) {
        subTasks.put(id, subtask);
        subTasks.get(id).setStatus(status);

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);

        updateEpic(epic, epicId);
    }

    public void updateEpic(Epic epic, int id) {
        epics.put(id, epic);

        if (epic.getSubTasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for(Subtask subtask : epic.getSubTasks()) {
            if (subtask.getStatus() != Status.NEW) { allNew = false; }
            if (subtask.getStatus() != Status.DONE) { allDone = false; }
        }

        if(allNew) { epic.setStatus(Status.NEW); }
        else if (allDone) { epic.setStatus(Status.DONE); }
        else { epic.setStatus(Status.IN_PROGRESS); }

    }

    public ArrayList<Subtask> getAllSubTasks (Epic epic) {
        return epic.getSubTasks();
    }
}

