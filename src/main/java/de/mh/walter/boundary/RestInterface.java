package de.mh.walter.boundary;

import de.mh.walter.boundary.bean.CreateTaskRequest;
import de.mh.walter.boundary.bean.CreateTaskListRequest;
import de.mh.walter.boundary.bean.MarkTaskDoneRequest;
import de.mh.walter.boundary.bean.UserOverview;
import de.mh.walter.control.Constants;
import de.mh.walter.control.OverviewController;
import de.mh.walter.control.TaskListManipulator;
import de.mh.walter.entity.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Constants.API_LOCATION)
public class RestInterface {

    @Autowired
    private OverviewController overviewController;
    @Autowired
    private TaskListManipulator taskListManipulator;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(path = "/done_history", method = RequestMethod.GET)
    public UserOverview getDoneHistory() {
        return overviewController.getDoneHistory(getUserId());
    }

    @RequestMapping(path = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "hi";
    }

    @RequestMapping(path = "/mark_task_done", method = RequestMethod.POST)
    public String markTaskDone(@RequestBody MarkTaskDoneRequest request) {
        return taskListManipulator.markTaskDone(request.getTaskId(), true, getUserId());
    }

    @RequestMapping(path = "/unmark_task_done", method = RequestMethod.POST)
    public String unmarkTaskDone(@RequestBody MarkTaskDoneRequest request) {
        return taskListManipulator.markTaskDone(request.getTaskId(), false, getUserId());
    }

    @RequestMapping(path = "/create_list", method = RequestMethod.POST)
    public String createList(@RequestBody CreateTaskListRequest request) {
        return taskListManipulator.createTaskListForUser(getUserId(), request.getName());
    }

    @RequestMapping(path = "/create_task", method = RequestMethod.POST)
    public String createTask(@RequestBody CreateTaskRequest request) {
        return taskListManipulator.createTaskInTaskList(request.getTaskListId(), request.getText(), getUserId());
    }

    @RequestMapping(path = "/overview", method = RequestMethod.GET)
    public UserOverview getOverview() {
        return overviewController.getOverviewForUser(getUserId());
    }

    private long getUserId() {
        User user = (User) request.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("no user found");
        }
        return user.getId();
    }
}
