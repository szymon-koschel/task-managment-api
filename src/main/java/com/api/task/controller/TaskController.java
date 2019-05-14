package com.api.task.controller;

import com.api.task.exception.ResourceNotFoundException;
import com.api.task.model.Task;
import com.api.task.respository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    //Get ALL
    @GetMapping("")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //Create
    @PostMapping("")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    //Get Single by id
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable(value = "id") Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }

    //Update
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable(value = "id") Long taskId, @Valid @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setTask_date(taskDetails.getTask_date());

        return taskRepository.save(task);
    }

    //Delete
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        taskRepository.delete(task);

        return ResponseEntity.ok().build();
    }
}
