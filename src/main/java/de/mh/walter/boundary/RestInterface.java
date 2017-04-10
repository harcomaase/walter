package de.mh.walter.boundary;

import de.mh.walter.boundary.bean.CreateTaskRequest;
import de.mh.walter.boundary.bean.CreateTaskListRequest;
import de.mh.walter.boundary.bean.MarkTaskDoneRequest;
import de.mh.walter.boundary.bean.UserOverview;
import de.mh.walter.control.OverviewController;
import de.mh.walter.control.TaskListManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "rest")
public class RestInterface {

    @Autowired
    private OverviewController overviewController;
    @Autowired
    private TaskListManipulator taskListManipulator;
    //
    private static final int TESTUSER = 1;

    @RequestMapping(path = "/done_history", method = RequestMethod.GET)
    public UserOverview getDoneHistory() {
        return overviewController.getDoneHistory(TESTUSER);
    }

    @RequestMapping(path = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "hi";
    }

    @RequestMapping(path = "/mark_task_done", method = RequestMethod.POST)
    public String markTaskDone(@RequestBody MarkTaskDoneRequest request) {
        return taskListManipulator.markTaskDone(request.getTaskId(), true, TESTUSER);
    }

    @RequestMapping(path = "/unmark_task_done", method = RequestMethod.POST)
    public String unmarkTaskDone(@RequestBody MarkTaskDoneRequest request) {
        return taskListManipulator.markTaskDone(request.getTaskId(), false, TESTUSER);
    }

    @RequestMapping(path = "/create_list", method = RequestMethod.POST)
    public String createList(@RequestBody CreateTaskListRequest request) {
        return taskListManipulator.createTaskListForUser(TESTUSER, request.getName());
    }

    @RequestMapping(path = "/create_task", method = RequestMethod.POST)
    public String createTask(@RequestBody CreateTaskRequest request) {
        return taskListManipulator.createTaskInTaskList(request.getTaskListId(), request.getText(), TESTUSER);
    }

    @RequestMapping(path = "/overview", method = RequestMethod.GET)
    public UserOverview getOverview() {
        return overviewController.getOverviewForUser(TESTUSER);
    }
}
