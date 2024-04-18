package account;

public class Account {
    private String accountNum;
    private String userId;
    private String productType;
    private int balance;

    public Account(){
        super();
    }

    public Account(String accountNum, String userId, String productType, int balance) {
        this.accountNum = accountNum;
        this.userId = userId;
        this.productType = productType;
        this.balance = balance;
    }

    public String getaccountNum() {
        return accountNum;
    }

    public void setaccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNum='" + accountNum + '\'' +
                ", userId='" + userId + '\'' +
                ", productType='" + productType + '\'' +
                ", balance=" + balance +
                '}';
    }
}
