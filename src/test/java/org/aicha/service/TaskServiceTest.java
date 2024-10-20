package org.aicha.service;

import org.aicha.model.Task;
import org.aicha.model.User;
import org.aicha.model.enums.TaskStatus;
import org.aicha.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<Task> result = taskService.getAllTasks();
        assertEquals(Collections.emptyList(), result);
        verify(taskRepository, times(1)).findAll();
    }
    @Test
    public void testGetTaskById_Success() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTaskById_InvalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.getTaskById(-1L));
        assertEquals("Invalid id", exception.getMessage());
        verify(taskRepository, never()).findById(anyLong());
    }


    @Test
    public void testCreateTask_InvalidTask() {
        Task invalidTask = new Task();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(invalidTask));
        assertEquals("Invalid task", exception.getMessage());
        verify(taskRepository, never()).create(any(Task.class));
    }


    @Test
    public void testUpdateTask_InvalidTask() {
        Task invalidTask = new Task();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(invalidTask));
        assertEquals("Invalid task", exception.getMessage());
        verify(taskRepository, never()).update(any(Task.class));
    }



    @Test
    public void testMarkTaskAsDone_InvalidTask() {
        Task invalidTask = new Task();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.markTaskAsDone(invalidTask));
        assertEquals("Invalid task", exception.getMessage());
        verify(taskRepository, never()).update(any(Task.class));
    }

    @Test
    public void testGetUserTasks() {
        User user = new User();
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findByAssignedTo(user)).thenReturn(tasks);

        List<Task> result = taskService.getUserTasks(user);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByAssignedTo(user);
    }



}