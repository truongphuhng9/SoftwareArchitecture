package com.orm.service;

import com.orm.entity.Task;

import java.util.List;

public interface TaskService {
    public List<Task> getTasksByUsername(String username);
    public boolean deleteTask(String username, int taskId);
    public Task createTask(String username, String taskName, String taskDesc);
}
