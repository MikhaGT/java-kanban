package ru.yandex.javacourse.lenkov.schedule;

import ru.yandex.javacourse.lenkov.schedule.manager.TaskManager;
import ru.yandex.javacourse.lenkov.schedule.task.Status;
import ru.yandex.javacourse.lenkov.schedule.task.Epic;
import ru.yandex.javacourse.lenkov.schedule.task.Subtask;
import ru.yandex.javacourse.lenkov.schedule.task.Task;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        int task1 = taskManager.createTask(new Task("Car", "Fix it"));
        int task2 = taskManager.createTask(new Task("Bed", "Make it"));

        int epic1 = taskManager.createEpic(new Epic("House", "Build it"));
        int subTask1 = taskManager.createSubTask(new Subtask("Build Walls", "Something about walls", epic1));
        int subTask2 = taskManager.createSubTask(new Subtask("Build Floor", "Something about floor", epic1));

        int epic2 = taskManager.createEpic(new Epic("Granma", "Call her"));
        int subTask3 = taskManager.createSubTask(new Subtask("Food", "Ask for her pancakes", epic2));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTasks());

        Task newTask1 = new Task("Car", "Fix it");
        newTask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(newTask1);

        Subtask newSubTask1 = new Subtask("Build Walls", "Something about walls", epic1);
        newSubTask1.setStatus(Status.DONE);
        taskManager.updateSubTask(newSubTask1);

        System.out.println(taskManager.getTask(task1));
        System.out.println(taskManager.getEpic(epic1));

        taskManager.removeTask(task1);
        taskManager.removeEpic(epic1);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTasks());
    }
}
