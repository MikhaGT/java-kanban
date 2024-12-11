package ru.yandex.javacourse.lenkov.schedule.manager;

import ru.yandex.javacourse.lenkov.schedule.task.Epic;
import ru.yandex.javacourse.lenkov.schedule.task.Subtask;
import ru.yandex.javacourse.lenkov.schedule.task.Task;

import java.util.List;

public interface TaskManager {
    Integer createTask(Task task);

    Integer createEpic(Epic epic);

    Integer createSubTask(Subtask subTask);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubTasks();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubTask(int id);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int id);

    void updateTask(Task task);

    void updateSubTask(Subtask subtask);

    void updateEpic(Epic epic);

    List<Subtask> getAllSubTasks(Epic epic);

    List<Task> getHistory();

}
