package de.mh.walter.control;

import de.mh.walter.boundary.bean.UserOverview;
import de.mh.walter.entity.Task;
import de.mh.walter.entity.TaskList;
import de.mh.walter.entity.User;
import de.mh.walter.entity.dao.TaskDao;
import de.mh.walter.entity.dao.TaskListDao;
import de.mh.walter.entity.dao.UserDao;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OverviewController {

    @Autowired
    UserDao userDao;
    @Autowired
    TaskListDao taskListDao;
    @Autowired
    TaskDao taskDao;

    public UserOverview getOverviewForUser(long userId) {
        User user = userDao.find(userId);
        if (user == null) {
            return null;
        }
        Instant now = Instant.now();
        List<TaskList> taskLists = taskListDao.findByUserId(userId);
        for (TaskList taskList : taskLists) {
            List<Task> tasks = taskDao.findByTaskListId(taskList.getId()).stream()
                    .filter(task -> !task.isDone()
                    || task.getDoneAt() == null
                    || Instant.ofEpochMilli(task.getDoneAt()).plus(Constants.SHOW_DONE_TASKS_IN_OVERVIEW_FOR).isAfter(now))
                    .collect(Collectors.toList());
            taskList.setTasks(tasks);
        }
        return new UserOverview(user.getEmail(), taskLists);
    }

    public UserOverview getDoneHistory(long userId) {
        User user = userDao.find(userId);
        if (user == null) {
            return null;
        }
        List<TaskList> taskLists = taskListDao.findByUserId(userId);
        for (TaskList taskList : taskLists) {
            List<Task> tasks = taskDao.findByTaskListId(taskList.getId()).stream()
                    .filter(Task::isDone)
                    .collect(Collectors.toList());
            taskList.setTasks(tasks);
        }
        return new UserOverview(user.getEmail(), taskLists);
    }
}
