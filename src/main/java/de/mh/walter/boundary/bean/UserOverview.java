package de.mh.walter.boundary.bean;

import de.mh.walter.entity.TaskList;
import java.util.List;

public class UserOverview {

    private final String userEmail;
    private final List<TaskList> taskLists;

    public UserOverview(String userEmail, List<TaskList> taskLists) {
        this.userEmail = userEmail;
        this.taskLists = taskLists;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<TaskList> getTaskLists() {
        return taskLists;
    }
}
