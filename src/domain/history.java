package src.domain;

public class history {
    private String type;
    private int balance;
    private int amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public history(String type, int balance, int amount) {
        this.type = type;
        this.balance = balance;
        this.amount = amount;
    }
}
