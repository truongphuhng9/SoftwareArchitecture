package com.orm.controller;

import com.orm.entity.Task;
import com.orm.payload.ResponsePayload;
import com.orm.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping("/{taskId}")
    public Object deleteTask(
            @PathVariable int taskId,
            HttpSession session
    ) {
        String username = String.valueOf(session.getAttribute("Username"));

        boolean isSuccess = this.taskService.deleteTask(username, taskId);

        ResponsePayload responsePayload = new ResponsePayload();
        if (!isSuccess) {
            session.setAttribute("Error", "Cannot delete task");
            responsePayload.setStatus(400);
            responsePayload.setSuccess(false);
        } else {
            responsePayload.setStatus(200);
            responsePayload.setSuccess(true);
            responsePayload.setData(taskId);
        }

        return responsePayload;
    }

    @PostMapping("")
    public Object createTask(
            @RequestParam("task-name") String taskName,
            @RequestParam("task-desc") String taskDesc,
            HttpSession session
    ) throws Exception {
        String username = String.valueOf(session.getAttribute("Username"));
        boolean isSuccess = this.taskService.createTask(username, taskName, taskDesc);

        ResponsePayload responsePayload = new ResponsePayload();
        if (!isSuccess) {
            session.setAttribute("Error", "Cannot create task");
            responsePayload.setStatus(400);
            responsePayload.setSuccess(false);
        } else {
            responsePayload.setStatus(200);
            responsePayload.setSuccess(true);
        }

        return responsePayload;
    }
}