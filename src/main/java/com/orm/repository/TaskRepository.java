package com.orm.repository;

import com.orm.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    public List<Task> getTasksByUsername(String username) {

        if (username.equals("someone")) {
            return new ArrayList<Task>() {
                {
                    add(new Task(1, "someone", "Do", "do task description"));
                    add(new Task(2, "someone", "Some", "do some description"));
                    add(new Task(3, "someone", "Thing", "do thing description"));
                    add(new Task(4, "someone", "Great", "do great description"));
                }
            };
        }
        ;

        return new ArrayList<Task>();
    }

    public Task getTaskById(int taskId) {
        switch (taskId) {
            case 1:
                return new Task(1, "someone", "Do", "do task description");
            case 2:
                return new Task(2, "someone", "Some", "do some description");
            case 3:
                return new Task(3, "someone", "Thing", "do thing description");
            case 4:
                return new Task(4, "someone", "Great", "do great description");
            default:
                return null;
        }
    }

    public Task createTask(String username, String taskName, String taskDesc) {
        return new Task(5, username, taskName, taskDesc);
    }

    public boolean deleteTaskById(int taskId) {
        return true;
    }

}
