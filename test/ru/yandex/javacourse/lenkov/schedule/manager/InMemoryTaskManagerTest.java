package ru.yandex.javacourse.lenkov.schedule.manager;

import ru.yandex.javacourse.lenkov.schedule.task.Status;
import ru.yandex.javacourse.lenkov.schedule.task.Epic;
import ru.yandex.javacourse.lenkov.schedule.task.Subtask;
import ru.yandex.javacourse.lenkov.schedule.task.Task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest {

    private TaskManager manager;
    private Epic epic1;
    private Epic epic2;
    private Subtask subtask1;
    private Subtask subtask2;
    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        manager = Managers.getDefault();
        task1 = new Task("Task1", "Description1");
        task2 = new Task("Task2", "Description2");
        manager.createTask(task1);
        manager.createTask(task2);

        epic1 = new Epic("Epic1", "Epic Description1");
        epic2 = new Epic("Epic2", "Epic Description2");
        manager.createEpic(epic1);
        manager.createEpic(epic2);

        subtask1 = new Subtask("Subtask1", "Subtask Description1", epic1.getId());
        subtask2 = new Subtask("Subtask2", "Subtask Description2", epic1.getId());
        manager.createSubTask(subtask1);
        manager.createSubTask(subtask2);
    }

    @Test
    public void testTasksEqualityById(){
        task2.setId(task1.getId());
        assertEquals(task1, task2);
    }

    @Test
    public void testEpicsEqualityById(){
        epic2.setId(epic1.getId());
        assertEquals(epic1, epic2);
    }

    @Test
    public void testSubTasksEqualityById(){
        subtask2.setId(subtask1.getId());
        assertEquals(subtask1, subtask2);
    }

    @Test
    public void testCannotAddEpicAsItsOwnSubtask() {
        epic1.addSubTaskId(epic1.getId());
        assertFalse(epic1.getSubTasksIds().contains(epic1.getId()));
    }

    @Test
    public void testCannotMakeSubtaskAsItsOwnEpic() {
        subtask1.setEpicId(subtask1.getId());
        assertNotEquals(subtask1.getId(), subtask1.getEpicId());
    }

    @Test
    public void testManagersGetDefault() {
        TaskManager defaultManager = Managers.getDefault();
        assertNotNull(defaultManager);
        assertInstanceOf(InMemoryTaskManager.class, defaultManager);

        HistoryManager defaultHistory = Managers.getDefaultHistory();
        assertNotNull(defaultHistory);
        assertInstanceOf(InMemoryHistoryManager.class, defaultHistory);
    }

    @Test
    public void testAddAndFindDifferentTaskTypes() {
        Task checkTask = manager.getTask(task1.getId());
        Epic checkEpic = manager.getEpic(epic1.getId());
        Subtask checkSubtask = manager.getSubTask(subtask1.getId());

        assertEquals(task1, checkTask);
        assertEquals(epic1, checkEpic);
        assertEquals(subtask1, checkSubtask);
    }

    @Test
    public void testNoIdConflict() {
        assertNotEquals(task1.getId(), epic1.getId());
        assertNotEquals(task1.getId(), subtask1.getId());
        assertNotEquals(epic1.getId(), subtask1.getId());
    }

    @Test
    public void testTaskImmutabilityAfterAdding() {
        manager.createTask(task1);
        assertEquals(task1, manager.getTask(task1.getId()));
    }

    @Test
    public void testHistoryManager() {
        manager.createTask(task1);
        manager.getTask(task1.getId());
        task2.setId(task1.getId());
        manager.updateTask(task2);
        assertEquals(task1.getName(), manager.getHistory().getFirst().getName());
        assertEquals(task1.getDescription(), manager.getHistory().getFirst().getDescription());
        assertEquals(task1.getStatus(), manager.getHistory().getFirst().getStatus());
    }
}