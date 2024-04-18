package src.domain;

public class Account {
    private String accountId;
    private int balance;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Account(String accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
    }
}
