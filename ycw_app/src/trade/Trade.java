package trade;

public class Trade {
    private int tradeId;
    private String userId;
    private String requestAccount;
    private String action;
    private String targetAccount;
    private int amount;
    private int reqBalance;
    private int tarBalance;

    public Trade() {
        super();
    }

    public Trade(int tradeId, String userId, String requestAccount, String action, String targetAccount, int amount, int reqBalance, int tarBalance) {
        this.tradeId = tradeId;
        this.userId = userId;
        this.requestAccount = requestAccount;
        this.action = action;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.reqBalance = reqBalance;
        this.tarBalance = tarBalance;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestAccount() {
        return requestAccount;
    }

    public void setRequestAccount(String requestAccount) {
        this.requestAccount = requestAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
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

    public int getReqBalance() {
        return reqBalance;
    }

    public void setReqBalance(int reqBalance) {
        this.reqBalance = reqBalance;
    }

    public int getTarBalance() {
        return tarBalance;
    }

    public void setTarBalance(int tarBalance) {
        this.tarBalance = tarBalance;
    }
}
