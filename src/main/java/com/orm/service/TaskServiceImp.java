package com.orm.service;

import com.orm.entity.Task;
import com.orm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImp implements TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUsername(String username) {
        return taskRepository.getTasksByUsername(username);
    }

    public boolean deleteTask(String username, int taskId) {
        Task task = this.taskRepository.getTaskById(taskId);
        if (username.equals(task.getUsername())) {
            return this.taskRepository.deleteTaskById(taskId);
        } else {
            return false;
        }
    }

    public Task createTask(String username, String taskName, String taskDesc){
        return this.taskRepository.createTask(username, taskName, taskDesc);
    }

}
