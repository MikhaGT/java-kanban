import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subTasks;
    private int currentId;

    public TaskManager(){
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        currentId = 0;
    }

    private int generateId() {
        return ++currentId;
    }

    public Integer createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public Integer createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    // Один момент, не совсем понимаю, нужно ли устанавливать epicId сабтаске, чтоб привязать ее к эпику определенному, или оно уже тут само как-то установлено?
    public Integer createSubTask(Subtask subTask) {
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        int id = generateId();
        subTask.setId(id);
        subTasks.put(id, subTask);
        epic.addSubTaskId(subTask.getId());
        updateEpicStatus(epicId);
        return id;
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
        for(Epic epic : epics.values()) {
            epic.cleanSubTaskIds();
            updateEpicStatus(epic.getId());
        }
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
        Epic epic = epics.remove(id); // а метод remove после удаления также возвращает ссылку на удаляемый объект?
        if (epic == null) { // Не совсем понимаю, а как этот эпик может быть null? Мы же пока только его из
            return;         // HashMap удалили, а сам объект не трогали, еще вопрос, объект эпик считается null если
                            // все его поля null или как?
        }
        for (Integer subTaskId : epic.getSubTasksIds()) {
            subTasks.remove(subTaskId);
        }
    }

    public void removeSubTask(int id) {
        Subtask subtask = subTasks.remove(id);

        if (subtask == null) {
            return;
        }

        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubTaskId(id);
        updateEpicStatus(epic.getId());
    }

    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);

        if (savedTask == null) {
            return;
        }

        tasks.put(id, task);
    }

    public void updateSubTask(Subtask subtask) {
        int id = subtask.getId();
        int epicId = subtask.getEpicId();

        Subtask savedSubtask = subTasks.get(id);
        if (savedSubtask == null) {
            return;
        }

        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        subTasks.put(id, subtask);
        updateEpicStatus(epicId);
    }

    public void updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }

        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic.getSubTasksIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subtaskId : epic.getSubTasksIds()) {
            if (subTasks.get(subtaskId).getStatus() != Status.NEW) { allNew = false; }
            if (subTasks.get(subtaskId).getStatus() != Status.DONE) { allDone = false; }
        }

        if (allNew) { epic.setStatus(Status.NEW); }
        else if (allDone) { epic.setStatus(Status.DONE); }
        else { epic.setStatus(Status.IN_PROGRESS); }
    }

    public ArrayList<Integer> getAllSubTasks (Epic epic) {
        return epic.getSubTasksIds();
    }
}

