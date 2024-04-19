package page;

public class Status {
    private boolean working;
    private String userId;
    private int workNum;

    public Status() {
        super();
    }

    public Status(boolean working) {
        this.working = working;
        this.userId = "guest";
        this.workNum = 0;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWorkNum() {
        return workNum;
    }

    public void setWorkNum(int workNum) {
        this.workNum = workNum;
    }
}
