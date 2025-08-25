package br.com.fiap.epictaska.task;

import br.com.fiap.epictaska.task.dto.TaskForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final MessageSource messageSource;

    public TaskController(TaskService taskService, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.taskService = taskService;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var avatar = user.getAttributes().get("avatar_url") != null?
                        user.getAttributes().get("avatar_url") :
                        user.getAttributes().get("picture");
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("user", user);
        model.addAttribute("avatar", avatar);
        return "index";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("taskForm", new TaskForm("", "", 50));
        return "form";
    }

    @PostMapping("/form")
    public String create(@Valid TaskForm taskForm, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()) return "form";

        taskService.save(taskForm.toModel());
        var message = messageSource.getMessage("task.created.success", null, LocaleContextHolder.getLocale());
        redirect.addFlashAttribute("message", message);
        return "redirect:/task";
    }

}
