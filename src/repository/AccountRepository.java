package src.repository;

public class AccountRepository {
    public static String createAccount(){
        return "INSERT INTO Account (AccountId, balance, User_UserId) VALUES (?, 0, ?)";
    }
    public static String deleteAccount(){
        return "DELETE FROM Account WHERE accountId = ? ";
    }
    public static String depositAccount(){
        return "UPDATE Account SET balance = balance + ? WHERE AccountId = ?";
    }
    public static String withdrawAccount(){
        return "UPDATE Account SET balance = balance - ? WHERE AccountId = ?";
    }
    public static String historyAccount(){
        return "INSERT INTO history (Account_AccountId, type, balance, amount, time) VALUES (?, ?, (SELECT balance FROM Account WHERE AccountId = ?), ?, ?)";
    }

    public static String findAll() {
        return "SELECT AccountId, balance FROM account WHERE User_UserId = ?";
    }

    public static String findOne() {
        return "SELECT AccountId FROM account WHERE AccountId = ?";
    }

    public static String balanceAccount() {
        return "SELECT balance FROM account WHERE AccountId = ?";
    }
}
