package ru.yandex.javacourse.lenkov.schedule.manager;

import ru.yandex.javacourse.lenkov.schedule.task.Status;
import ru.yandex.javacourse.lenkov.schedule.task.Epic;
import ru.yandex.javacourse.lenkov.schedule.task.Subtask;
import ru.yandex.javacourse.lenkov.schedule.task.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subTasks;
    private final HistoryManager historyManager;
    private int currentId;

    public InMemoryTaskManager(){
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        currentId = 0;
    }

    @Override
    public Integer createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public Integer createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
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

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        deleteAllSubTasks();
    }

    @Override
    public void deleteAllSubTasks() {
        for(Epic epic : epics.values()) {
            epic.cleanSubTaskIds();
            updateEpicStatus(epic.getId());
        }
        subTasks.clear();
    }

    @Override
    public Task getTask(int id) {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubTask(int id) {
        final Subtask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }

        for (Integer subTaskId : epic.getSubTasksIds()) {
            subTasks.remove(subTaskId);
        }
    }

    @Override
    public void removeSubTask(int id) {
        Subtask subtask = subTasks.remove(id);

        if (subtask == null) {
            return;
        }

        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubTaskId(id);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);

        if (savedTask == null) {
            return;
        }

        tasks.put(id, task);
    }

    @Override
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

    @Override
    public void updateEpic(Epic epic) {
        final Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }

        epic.setSubTasksIds(savedEpic.getSubTasksIds());
        epic.setStatus(savedEpic.getStatus());
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Subtask> getAllSubTasks(Epic epic) {
        ArrayList<Subtask> epicSubTasks = new ArrayList<>();
        for(Integer id : epic.getSubTasksIds()) {
            epicSubTasks.add(subTasks.get(id));
        }

        return epicSubTasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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
            if (subTasks.get(subtaskId).getStatus() != Status.NEW) {
                allNew = false;
            }
            if (subTasks.get(subtaskId).getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private int generateId() {
        return ++currentId;
    }

}
