package de.mh.walter.boundary.bean;

public class CreateTaskRequest {

    private long taskListId;
    private String text;

    public long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(long taskListId) {
        this.taskListId = taskListId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
