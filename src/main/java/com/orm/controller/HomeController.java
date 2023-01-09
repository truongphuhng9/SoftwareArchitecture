package com.orm.controller;

import com.orm.entity.Task;
import com.orm.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private TaskService taskService;
    @Autowired
    public void setTaskService(TaskService taskService){
        this.taskService=taskService;
    }

    @GetMapping({"/home","/",""})
    public String index(
            HttpSession session,
            ModelMap model
    ) throws Exception {
        String username = session.getAttribute("Username").toString();
        String fullname = session.getAttribute("Fullname").toString();
        List<Task> tasks = this.taskService.getTasksByUsername(username);

        model.addAttribute("fullname", fullname);
        model.addAttribute("tasks", tasks);
        return "home";
    }
}
