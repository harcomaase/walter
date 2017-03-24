package de.mh.walter.entity.dao;

import de.mh.walter.entity.TaskList;
import de.mh.walter.entity.repository.TaskListRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskListDao {

    @Autowired
    TaskListRepository taskListRepository;

    public List<TaskList> findByUserId(long userId) {
        return taskListRepository.findByUserId(userId);
    }

    public TaskList findByUserIdAndName(long userId, String name) {
        return taskListRepository.findByUserIdAndName(userId, name);
    }

    public void createTaskList(long userId, String name) {
        long now = System.currentTimeMillis();
        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setUserId(userId);
        taskList.setCreated(now);
        taskList.setLastChange(now);
        taskListRepository.save(taskList);
    }

    public TaskList findById(long taskListId) {
        return taskListRepository.findOne(taskListId);
    }
}
