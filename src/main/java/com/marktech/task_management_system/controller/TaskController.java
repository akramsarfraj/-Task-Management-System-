package com.marktech.task_management_system.controller;

import com.marktech.task_management_system.model.Task;
import com.marktech.task_management_system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping("/api/create-task")
    public ResponseEntity<Task> createTask (@RequestBody Task task){
        return service.createTask(task);
    }

    @PutMapping("/api/update-task/{id}")
    public ResponseEntity<String> updateTask(@RequestBody Task task, @PathVariable(name = "id") int id){
        return service.updateTask(task,id);
    }

    @GetMapping("/api/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(name = "id") int id){
        return ResponseEntity.ok().body( service.getTaskById(id) );
    }

    @GetMapping("/api/get-all")
    public Page<Task> getAllTask(
          @RequestParam(name = "page",defaultValue = "0")  int page,
          @RequestParam(name = "limit",defaultValue = "5")  int limit
    ){
        return service.getAllTask(page,limit);
    }

    @DeleteMapping("/api/delete-task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") int id){
        return ResponseEntity.ok().body( service.deleteTask(id) );
    }

    @GetMapping("/api/filter")
    public List<Task> fetchBy(
            @RequestParam(name = "priority") String priority,
            @RequestParam(name = "status") String status
    ){
        return  service.fetchBy(priority,status);
    }
}
