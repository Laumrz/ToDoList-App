package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks() throws Exception {
        Task task1 = new Task("Revisar documentación", false);
        Task task2 = new Task("Actualizar dependencias", true);
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskService.getAllTask()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].task").value("Revisar documentación"))
                .andExpect(jsonPath("$[1].task").value("Actualizar dependencias"));
    }

    @Test
    void getAllCompletedTasks() throws Exception {
        Task task1 = new Task("Tarea 1", true);
        Task task2 = new Task("Tarea 2", true);
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskService.findAllCompletedTask()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].task").value("Tarea 1"))
                .andExpect(jsonPath("$[1].task").value("Tarea 2"));
    }

    @Test
    void getAllIncompleteTasks() throws Exception {
        Task task1 = new Task("Tarea 1", false);
        Task task2 = new Task("Tarea 2", false);
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskService.findAllInCompleteTask()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks/incomplete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].task").value("Tarea 1"))
                .andExpect(jsonPath("$[1].task").value("Tarea 2"));
    }

    @Test
    void createTask() throws Exception {
        Task newTask = new Task("Nueva tarea", false);
        when(taskService.createNewTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/v1/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"task\": \"Nueva tarea\", \"completed\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").value("Nueva tarea"));
    }

    @Test
    void updateTask() throws Exception {
        Task updatedTask = new Task("Tarea actualizada", true);
        updatedTask.setId(1L);
        when(taskService.updateTask(any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"task\": \"Tarea actualizada\", \"completed\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").value("Tarea actualizada"));
    }

    @Test
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isOk());
    }
}
