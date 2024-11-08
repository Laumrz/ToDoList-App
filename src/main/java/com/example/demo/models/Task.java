package com.example.demo.models;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Esta es la clave primaria que se generará automáticamente.
    private String task; // Descripción de la tarea.
    private boolean completed; // Estado de la tarea (completada o no).

    // Constructor por defecto
    public Task() {
    }

    // Constructor parametrizado
    public Task(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
