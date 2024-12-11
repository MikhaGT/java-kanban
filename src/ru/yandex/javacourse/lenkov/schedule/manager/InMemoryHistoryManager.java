package ru.yandex.javacourse.lenkov.schedule.manager;

import ru.yandex.javacourse.lenkov.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int HISTORY_SIZE = 10;
    private final List<Task> history = new ArrayList<>(HISTORY_SIZE);

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
