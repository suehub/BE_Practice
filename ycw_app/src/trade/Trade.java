package trade;

public class Trade {
    private String request_account;
    private String action;
    private String target_account;
    private int amount;
    private int result;

    public Trade(String request_account, String action, String target_account, int amount, int result) {
        this.request_account = request_account;
        this.action = action;
        this.target_account = target_account;
        this.amount = amount;
        this.result = result;
    }

    public String getRequest_account() {
        return request_account;
    }

    public void setRequest_account(String request_account) {
        this.request_account = request_account;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTarget_account() {
        return target_account;
    }

    public void setTarget_account(String target_account) {
        this.target_account = target_account;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
