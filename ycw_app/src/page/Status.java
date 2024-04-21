package page;

public class Status {
    private String workFlow;
    private String userId;
    private String workName;
    private String data;
    private String message;


    public Status() {
        super();
    }

    public Status(String workflow) {
        this.workFlow = workflow;
        this.userId = "non_signed_guest";
        this.workName = "log_in";
        this.data = "";
        this.message = "";
    }

    public String getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(String workFlow) {
        this.workFlow = workFlow;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
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
