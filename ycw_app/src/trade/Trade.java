package trade;

public class Trade {
    private int tradeId;
    private String requestAccount;
    private String action;
    private String targetAccount;
    private int amount;
    private int balance;

    public Trade() {
        super();
    }

    public Trade(int tradeId, String requestAccount, String action, String targetAccount, int amount, int balance) {
        this.tradeId = tradeId;
        this.requestAccount = requestAccount;
        this.action = action;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.balance = balance;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", requestAccount='" + requestAccount + '\'' +
                ", action='" + action + '\'' +
                ", targetAccount='" + targetAccount + '\'' +
                ", amount=" + amount +
                ", balance=" + balance +
                '}';
    }
}
