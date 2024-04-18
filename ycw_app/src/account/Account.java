package account;

public class Account {
    private String account_number;
    private String product_type;
    private int balance;

    public Account(String account_number, String product_type, int balance) {
        this.account_number = account_number;
        this.product_type = product_type;
        this.balance = balance;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
