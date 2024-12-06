package ru.yandex.javacourse.lenkov.schedule.task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIds;

    public Epic(String name, String description) {
        super(name, description);
        subTasksIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void addSubTaskId(Integer id) {
        subTasksIds.add(id);
    }

    public void removeSubTaskId(Integer id) {
        subTasksIds.remove(id);
    }

    public void cleanSubTaskIds() {
        subTasksIds.clear();
    }
}
