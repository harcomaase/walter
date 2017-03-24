package de.mh.lists.control;

import de.mh.lists.entity.Task;
import de.mh.lists.entity.TaskList;
import de.mh.lists.entity.User;
import de.mh.lists.entity.dao.TaskDao;
import de.mh.lists.entity.dao.TaskListDao;
import de.mh.lists.entity.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskListManipulator {

    @Autowired
    UserDao userDao;
    @Autowired
    TaskListDao taskListDao;
    @Autowired
    TaskDao taskDao;

    public String createTaskListForUser(long userId, String name) {
        User user = userDao.find(userId);
        if (user == null) {
            return "illegal user";
        }
        if (taskListDao.findByUserIdAndName(userId, name) != null) {
            return "tasklist with same name already exists";
        }
        taskListDao.createTaskList(userId, name);
        return "OK";
    }

    public String createTaskInTaskList(long taskListId, String text, long userId) {
        TaskList taskList = taskListDao.findById(taskListId);
        if (taskList == null) {
            return "illegal tasklist";
        }
        if (userId != taskList.getUserId()) {
            return "illegal owner";
        }
        taskDao.createTask(taskListId, text);
        return "OK";
    }

    public String markTaskDone(long taskId, boolean done, long userId) {
        Task task = taskDao.findById(taskId);
        if (task == null) {
            return "invalid task";
        }
        TaskList taskList = taskListDao.findById(task.getTaskListId());
        if (taskList == null) {
            return "illegal tasklist";
        }
        if (userId != taskList.getUserId()) {
            return "illegal owner";
        }
        task.setDone(done);
        task.setDoneAt(done ? now() : null);
        taskDao.update(task);
        return "OK";
    }

    private long now() {
        return System.currentTimeMillis();
    }
}
