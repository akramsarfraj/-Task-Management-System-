package com.marktech.task_management_system.repository;

import com.marktech.task_management_system.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByStatusAndPriority(String status,String priority);
}
