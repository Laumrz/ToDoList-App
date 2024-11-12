package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Estudiar Spring", false);
    }

    @Test
    void getId() {
        task.setId(1L);
        assertEquals(1L, task.getId(), "El id debe ser 1");
    }

    @Test
    void setId() {
        task.setId(2L);
        assertEquals(2L, task.getId(), "El id debe ser 2");
    }

    @Test
    void getTask() {
        assertEquals("Estudiar Spring", task.getTask(), "La tarea debe ser 'Estudiar Spring'");
    }

    @Test
    void setTask() {
        task.setTask("Leer documentación");
        assertEquals("Leer documentación", task.getTask(), "La tarea debe ser 'Leer documentación'");
    }

    @Test
    void isCompleted() {
        assertFalse(task.isCompleted(), "La tarea debe estar incompleta");
    }

    @Test
    void setCompleted() {
        task.setCompleted(true);
        assertTrue(task.isCompleted(), "La tarea debe estar completada");
    }
}
