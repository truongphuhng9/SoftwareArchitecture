package com.orm.repository;

import com.orm.MyORM.Dialect.Condition.EqualCondition;
import com.orm.MyORM.Dialect.Value.FieldValue;
import com.orm.MyORM.Dialect.Value.IntegerValue;
import com.orm.MyORM.Dialect.Value.StringValue;
import com.orm.MyORM.Query.DeleteQuery;
import com.orm.MyORM.Query.InsertQuery;
import com.orm.MyORM.Query.SelectQuery;
import com.orm.MyORM.Repository.RepositoryImpl;
import com.orm.entity.Task;

import java.io.File;
import java.util.List;

public class TaskRepository extends RepositoryImpl<Task, Integer> {

    public TaskRepository() {
        super(Task.class, Integer.class);
    }

    public List<Task> getTasksByUserId(int userId) throws Exception {
        SelectQuery selectQuery = new SelectQuery();
        String sql = selectQuery.select().from("tasks").where(new EqualCondition(new FieldValue("userId"), new IntegerValue(userId))).build();
        return (List<Task>) this.executeList(sql);
    }

    public Task getTaskById(int taskId) {

        switch (taskId) {
            case 1:
                return new Task(1, 1, "Do", "do task description");
            case 2:
                return new Task(2, 1, "Some", "do some description");
            case 3:
                return new Task(3, 1, "Thing", "do thing description");
            case 4:
                return new Task(4, 1, "Great", "do great description");
            default:
                return null;
        }
    }

    public boolean createTask(int userId, String taskName, String taskDesc) throws Exception {
        Task task = new Task();
        task.setUserId(userId);
        task.setTaskName(taskName);
        task.setTaskDesc(taskDesc);

        return this.insertOne(task);
    }

    public boolean deleteTaskById(int taskId) {

        DeleteQuery deleteQuery = new DeleteQuery();
        String tx = deleteQuery
                .delete_from("tasks")
                .where(new EqualCondition(new FieldValue("id"),new IntegerValue(taskId)))
                .build();

        try {
            this.execute(tx);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
