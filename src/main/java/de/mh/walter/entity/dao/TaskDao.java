package de.mh.walter.entity.dao;

import de.mh.walter.entity.Task;
import de.mh.walter.entity.repository.TaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskDao {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> findByTaskListId(long taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    public void createTask(long taskListId, String text) {
        long now = System.currentTimeMillis();
        Task task = new Task();
        task.setCreated(now);
        task.setDone(false);
        task.setDoneAt(null);
        task.setTaskListId(taskListId);
        task.setText(text);
        taskRepository.save(task);
    }

    public Task findById(long taskId) {
        return taskRepository.findOne(taskId);
    }

    public void update(Task task) {
        taskRepository.save(task);
    }
}
