package client;

import controller.Flow;
import controller.Tag;

import java.util.HashMap;
import java.util.Map;

public class Status {
    private Flow workFlow;
    private Tag workTag;
    private String message;
    private String userId;
    private Map<String, String> data;


    public Status() {
        this.workFlow = Flow.RUN;
        this.workTag = Tag.LOG_IN;
        this.message = Tag.DEFAULT_DATA.getTag();
        this.userId = Flow.NEW_GUEST.getFlow();
        this.data = new HashMap<String, String>();
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

    public Tag getWorkTag() {
        return workTag;
    }

    public void setWorkTag(Tag workTag) {
        this.workTag = workTag;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getDataValue(String key) {
        return data.get(key);
    }

    public void setData(Map<String, String> data){
        this.data = data;
    }

    public void setDataValue(Tag method, String key, String value) {
        if (method.equals(Tag.PUT_DATA)) {
            data.put(key, value);
        } else if (method.equals(Tag.UPDATE_DATA)) {
            data.replace(key, value);
        }
    }

    public void deleteDataValue(String key) {
        data.remove(key);
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
                ", workTag='" + workTag + '\'' +
                ", data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
