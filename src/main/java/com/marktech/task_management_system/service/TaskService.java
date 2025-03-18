package com.marktech.task_management_system.service;

import com.marktech.task_management_system.exception.TaskNotFoundException;
import com.marktech.task_management_system.model.Task;
import com.marktech.task_management_system.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public ResponseEntity<Task> createTask(Task task){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(task));
    }

    @CachePut(cacheNames = "task",key = "#id")
    public Task updateTask(Task task, int id){
        Optional<Task> optional =repository.findById(id);
        if(optional.isPresent()){
            Task taskDb = optional.get();
            taskDb.setDescription(task.getDescription());
            taskDb.setStatus(task.getStatus());
            taskDb.setPriority(task.getPriority());
            taskDb.setTitle(task.getTitle());
            return repository.save(taskDb);

        }
        throw new TaskNotFoundException("Task Not Found");
    }

    public Page<Task> getAllTask(int page, int limit) {

        Pageable pageable = PageRequest.of(page,limit);
        return repository.findAll(pageable);
    }

    @CacheEvict(value = "task", key = "#id")
    public String deleteTask(int id){
        Optional<Task> optional =repository.findById(id);
        if(optional.isPresent()){
            Task task = optional.get();
            repository.delete(task);
            return "Task Deleted";
        }
        throw new TaskNotFoundException("Task Not Found");
    }

    @Cacheable(value = "task",key = "#id")
    public Task getTaskById(int id){
        Optional<Task> optional =repository.findById(id);
        if(optional.isPresent()){
            Task task = optional.get();
            return task;
        }
        throw new TaskNotFoundException("Task Not Found");
    }

    @Cacheable(value = "task")
    public List<Task> fetchBy(String priority, String status) {
       List<Task> tasks = repository.findByStatusAndPriority(status,priority);
       return tasks;
    }
}
