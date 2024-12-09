package ru.yandex.javacourse.lenkov.schedule.manager;

public class Managers {
    static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
