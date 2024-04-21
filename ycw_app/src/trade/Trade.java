package trade;

public class Trade {
    private int tradeId;
    private String action;
    private String requestAccount;
    private String targetAccount;
    private int amount;
    private int reqBalance;
    private int tarBalance;

    public Trade() {
        super();
    }

    public Trade(int tradeId, String action, String requestAccount, String targetAccount, int amount, int reqBalance, int tarBalance) {
        this.tradeId = tradeId;
        this.action = action;
        this.requestAccount = requestAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.reqBalance = reqBalance;
        this.tarBalance = tarBalance;
    }

    public Trade(int tradeId, String action, String requestAccount, String targetAccount, int amount) {
        this.tradeId = tradeId;
        this.action = action;
        this.requestAccount = requestAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
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

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", requestAccount='" + requestAccount + '\'' +
                ", action='" + action + '\'' +
                ", targetAccount='" + targetAccount + '\'' +
                ", amount=" + amount +
                ", reqBalance=" + reqBalance +
                ", tarBalance=" + tarBalance +
                '}';
    }
}
