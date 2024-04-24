package client;

import controller.Flow;
import controller.Tag;

public class Status {
    private Flow workFlow;
    private Tag workName;
    private String message;
    private String userId;
    private String data;



    public Status() {
        super();
    }

    public Status(Flow workflow) {
        this.workFlow = workflow;
        this.workName = Tag.LOG_IN;
        this.message = "";
        this.userId = Flow.NEW_GUEST.getFlow();
        this.data = "";
    }

    public Flow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(Flow workFlow) {
        this.workFlow = workFlow;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Tag getWorkName() {
        return workName;
    }

    public void setWorkName(Tag workName) {
        this.workName = workName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "workFlow='" + workFlow + '\'' +
                ", userId='" + userId + '\'' +
                ", workName='" + workName + '\'' +
                ", data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
