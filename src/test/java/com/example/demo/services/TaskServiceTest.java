package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void createNewTask() {
        Task newTask = new Task("Escribir documentación de proyecto", false);

        when(taskRepository.save(newTask)).thenReturn(newTask);

        Task result = taskService.createNewTask(newTask);

        assertEquals(newTask.getTask(), result.getTask());
        assertEquals(newTask.isCompleted(), result.isCompleted());
    }

    @Test
    void getAllTask() {
        Task task1 = new Task("Revisión de código", false);
        Task task2 = new Task("Realizar pruebas unitarias", true);
        List<Task> mockTasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTask();

        assertEquals(2, result.size(), "Se esperaba que hubiera 2 tareas");
        assertEquals("Revisión de código", result.get(0).getTask());
        assertEquals("Realizar pruebas unitarias", result.get(1).getTask());
    }

    @Test
    void findTaskById() {
        Task task = new Task("Corregir bugs", true);
        task.setId(1L);

        when(taskRepository.getById(1L)).thenReturn(task);

        Task result = taskService.findTaskById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Corregir bugs", result.getTask());
        assertTrue(result.isCompleted(), "La tarea debe estar completada");
    }

    @Test
    void findAllCompletedTask() {
        Task task1 = new Task("Revisar pull request", true);
        Task task2 = new Task("Actualizar ", true);
        List<Task> completedTasks = Arrays.asList(task1, task2);

        when(taskRepository.findByCompletedTrue()).thenReturn(completedTasks);

        List<Task> result = taskService.findAllCompletedTask();

        assertEquals(2, result.size(), "Se esperaba que hubiera 2 tareas completadas");
        assertTrue(result.get(0).isCompleted(), "La primera tarea debe estar completada");
        assertTrue(result.get(1).isCompleted(), "La segunda tarea debe estar completada");
    }

    @Test
    void findAllInCompleteTask() {
        Task task1 = new Task("Asistir a reunion", false);
        Task task2 = new Task("Planificar reunion", false);
        List<Task> incompleteTasks = Arrays.asList(task1, task2);

        when(taskRepository.findByCompletedFalse()).thenReturn(incompleteTasks);

        List<Task> result = taskService.findAllInCompleteTask();

        assertEquals(2, result.size(), "Se esperaba que hubiera 2 tareas incompletas");
        assertFalse(result.get(0).isCompleted(), "La primera tarea debe estar incompleta");
        assertFalse(result.get(1).isCompleted(), "La segunda tarea debe estar incompleta");
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);

        assertDoesNotThrow(() -> verify(taskRepository).deleteById(taskId), "No se elimino la tarea.");
    }


    @Test
    void updateTask() {
        Task task = new Task("Finalizar proyecto", true);
        task.setId(1L);

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateTask(task);

        assertEquals(1L, result.getId());
        assertEquals("Finalizar proyecto", result.getTask(), "El nombre de la tarea debe ser 'Finalizar proyecto'");
        assertTrue(result.isCompleted(), "La tarea debe estar completa después de la actualización");
    }

    @Test
    void createNewTask_WhenRepositoryFails() {
        Task newTask = new Task("Depurar codigo", false);

        when(taskRepository.save(newTask)).thenThrow(new RuntimeException("Error en la base de datos"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.createNewTask(newTask);
        });

        assertEquals("Error en la base de datos", exception.getMessage());
    }

}