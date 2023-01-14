package com.orm.service;

import com.orm.MyORM.Query.SelectQuery;
import com.orm.entity.Task;
import com.orm.entity.User;
import com.orm.repository.TaskRepository;
import com.orm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImp implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskServiceImp() {
        this.taskRepository = new TaskRepository();
        this.userRepository = new UserRepository();
    }

    public List<Task> getTasksByUsername(String username) throws Exception {
        User user = this.userRepository.getUserByUsername(username);
        return taskRepository.getTasksByUserId(user.getId());
    }

    public boolean deleteTask(String username, int taskId) throws Exception {
        Task task = this.taskRepository.getTaskById(taskId);
        if (username.equals("someone")) {
            return this.taskRepository.deleteTaskById(taskId);
        } else {
            return false;
        }
    }

    public boolean createTask(String username, String taskName, String taskDesc) throws Exception {
        User user = this.userRepository.getUserByUsername(username);
        return this.taskRepository.createTask(user.getId(), taskName, taskDesc);
    }

}
