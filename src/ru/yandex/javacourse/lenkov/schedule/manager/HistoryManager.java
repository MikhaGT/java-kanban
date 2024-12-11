package ru.yandex.javacourse.lenkov.schedule.manager;

import ru.yandex.javacourse.lenkov.schedule.task.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();
}
