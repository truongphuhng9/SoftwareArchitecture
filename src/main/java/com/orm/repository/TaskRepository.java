package com.orm.repository;

import com.orm.MyORM.Dialect.Condition.EqualCondition;
import com.orm.MyORM.Dialect.Value.FieldValue;
import com.orm.MyORM.Dialect.Value.IntegerValue;
import com.orm.MyORM.Query.SelectQuery;
import com.orm.MyORM.Repository.RepositoryImpl;
import com.orm.entity.Task;

import java.util.List;
import java.util.Optional;

public class TaskRepository extends RepositoryImpl<Task, Integer> {

    public TaskRepository() {
        super(Task.class, Integer.class);
    }

    public List<Task> getTasksByUserId(int userId) throws Exception {
        SelectQuery selectQuery = new SelectQuery();
        String sql = selectQuery.select().from("tasks").where(new EqualCondition(new FieldValue("userId"), new IntegerValue(userId))).build();
        return (List<Task>) this.executeList(sql);
    }

    public Task getTaskById(int taskId) throws Exception {
        Optional<Task> task = this.findById(taskId);
        return task.orElse(null);
    }

    public boolean createTask(int userId, String taskName, String taskDesc) throws Exception {
        Task task = new Task();
        task.setUserId(userId);
        task.setTaskName(taskName);
        task.setTaskDesc(taskDesc);

        return this.insertOne(task);
    }

    public boolean deleteTaskById(int taskId) {
        try {
            this.deleteById(taskId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}