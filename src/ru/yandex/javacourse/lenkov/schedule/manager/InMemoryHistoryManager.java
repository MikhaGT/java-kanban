package ru.yandex.javacourse.lenkov.schedule.manager;

import ru.yandex.javacourse.lenkov.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>(HISTORY_SIZE);
    private final static int HISTORY_SIZE = 10;

    public void add(Task task) {
        if (task == null) {
            return;
        }

        if (history.size() > HISTORY_SIZE) {
            history.removeFirst();
        }

        history.add(task);
    }

    public List<Task> getHistory() {
        return history;
    }
}
