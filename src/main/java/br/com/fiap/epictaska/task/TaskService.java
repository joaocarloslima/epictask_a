package br.com.fiap.epictaska.task;

import br.com.fiap.epictaska.user.User;
import br.com.fiap.epictaska.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final MessageSource messageSource;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, MessageSource messageSource, UserService userService) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    public List<Task> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        var message = messageSource.getMessage("task.not.found", null, LocaleContextHolder.getLocale());
        taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(message)
        );
        taskRepository.deleteById(id);
    }

    public void pickTask(Long id, User user) {

        var message = messageSource.getMessage("task.not.found", null, LocaleContextHolder.getLocale());
        var task = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(message)
        );
        task.setUser(user);
        taskRepository.save(task);
    }

    public void dropTask(Long id, User user) {

        var message = messageSource.getMessage("task.not.found", null, LocaleContextHolder.getLocale());
        var task = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(message)
        );
        if(!task.getUser().equals(user)){
            var error = messageSource.getMessage("task.assigned.to.other.user", null, LocaleContextHolder.getLocale());
            throw new IllegalStateException(error);
        }
        task.setUser(null);
        taskRepository.save(task);
    }

    public void incrementTaskStatus(Long id, User user) {

        var message = messageSource.getMessage("task.not.found", null, LocaleContextHolder.getLocale());
        var task = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(message)
        );
        task.setStatus(task.getStatus() + 10);

        if (task.getStatus() > 100) {
            task.setStatus(100);
        }

        if(task.getStatus() == 100){
            userService.addScore(user, task.getScore());
        }

        taskRepository.save(task);
    }

    public void decrementTaskStatus(Long id, User user) {

        var message = messageSource.getMessage("task.not.found", null, LocaleContextHolder.getLocale());
        var task = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(message)
        );
        task.setStatus(task.getStatus() - 10);

        if (task.getStatus() < 0) {
            task.setStatus(0);
        }

        taskRepository.save(task);
    }


    public List<Task> getUndoneTasks() {
        return taskRepository.findByStatusLessThan(100);
    }
}
