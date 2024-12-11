package ru.yandex.javacourse.lenkov.schedule.task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTasksIds;

    public Epic(String name, String description) {
        super(name, description);
        subTasksIds = new ArrayList<>();
    }

    public List<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void setSubTasksIds(List<Integer> ids) {
        subTasksIds = ids;
    }

    public void addSubTaskId(Integer id) {
        if(this.id == id) {
            return;
        }
        subTasksIds.add(id);
    }

    public void removeSubTaskId(Integer id) {
        subTasksIds.remove(id);
    }

    public void cleanSubTaskIds() {
        subTasksIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksIds=" + subTasksIds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
