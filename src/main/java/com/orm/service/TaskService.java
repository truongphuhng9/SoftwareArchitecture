package com.orm.service;

import com.orm.entity.Task;

import java.util.List;

public interface TaskService {
    public List<Task> getTasksByUsername(String username) throws Exception;

    public boolean deleteTask(String username, int taskId);

    public boolean createTask(String username, String taskName, String taskDesc) throws Exception;
}
