package accountManager;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.System.getenv;

/**
 * ....
 */
public class AccountManager {
    static Map<String, String> env = getenv();
    private static final String URL = "jdbc:mysql://localhost:3306/javabank";
    private static final String USERNAME = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    // 계좌 조회 기능
    public Map<String, Double> checkAccountDetails(String id) {

        Map<String, Double> accountLists = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT account_number, balance FROM Accounts WHERE user_id = ?")) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String accountNumber = resultSet.getString("account_number");
                    double balance = resultSet.getDouble("balance");
                    accountLists.put(accountNumber, balance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountLists; // 계좌 목록 반환
    }
//        ResultSet resultSet = null; // ResultSet 선언
//        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
//            // 잔액 조회
//            double balance = checkBalance(connection, accountNumber);
//            if (balance >= 0) {
//                System.out.printf("계좌 잔액: %.0f원 \n", balance);

//                // 최근 내역 조회
//                resultSet = getRecentTransactions(connection, accountNumber);
//                if (resultSet != null) {
//                    System.out.println("최근 내역:");
//                    while (resultSet.next()) {
//                        String transactionType = resultSet.getString("transaction_type");
//                        double amount = resultSet.getDouble("transaction_amount"); // 수정된 부분
//                        System.out.println("거래 유형: " + transactionType + ", 금액: " + amount);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.getStackTrace();
//        }
//        finally {
//            // ResultSet이 null이 아니라면 닫기
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    e.getStackTrace();
//                }
//            }
//        }


    // 잔액 조회 기능
    public double checkBalance(Connection connection, String accountNumber) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ?")) {
            statement.setString(1, accountNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                } else {
                    System.out.println("해당 계좌가 존재하지 않습니다.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.getStackTrace();
            return -1; // 오류 반한
        }
    }

//    private ResultSet getRecentTransactions(Connection connection, String accountNumber) throws SQLException {
//        String sql = "SELECT transaction_type, transaction_amount FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC LIMIT 10";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, accountNumber);
//        ResultSet resultSet = statement.executeQuery();
//
//        statement.close();
//        return resultSet;
//    }

    // 입금 기능
    public void deposit(String id, String accountNumber, double amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement depositStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance + ? WHERE account_number = ?");
             PreparedStatement transactionStatement = connection.prepareStatement("INSERT INTO Transactions (transaction_type, transaction_amount, transaction_date, recipient_account) VALUES (?, ?, NOW(), ?)")) {
            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, accountNumber);

            int rowsUpdated = depositStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("입금이 완료되었습니다.");
                double balance = checkBalance(connection, accountNumber);
                if (balance >= 0) {
                    System.out.printf("계좌 잔액: %.0f원 \n", balance);
                }

                // Transaction 테이블에 거래 내역 저장
                transactionStatement.setString(1, "입금");
                transactionStatement.setDouble(2, amount);
                transactionStatement.setString(3, accountNumber);
                transactionStatement.executeUpdate();
            } else {
                System.out.println("입금에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 출금 기능
    public void withdraw(String accountNumber, double amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?")) {
            statement.setDouble(1, amount);
            statement.setString(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("출금이 완료되었습니다.");
                double balance = checkBalance(connection, accountNumber);
                if (balance >= 0) {
                    System.out.printf("계좌 잔액: %.0f원 \n", balance);
                }
            } else {
                System.out.println("출금에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

    // 계좌 송금
    public void transfer(String userAccountNumber, String transferAccountNumber, double amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
             PreparedStatement statement2 = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {
            statement.setDouble(1, amount);
            statement.setString(2, userAccountNumber);
            statement2.setDouble(1, amount);
            statement2.setString(2, transferAccountNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("송금이 완료되었습니다.");
                double balance = checkBalance(connection, userAccountNumber);
                if (balance >= 0) {
                    System.out.printf("계좌 잔액: %.0f원 \n", balance);
                }
            } else {
                System.out.println("송금에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    // 계좌 생성 기능
    public void createAccount(String userName) {
        Random rand = new Random();
        int accountNumber = rand.nextInt(1000000000);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts(account_number, user_name, balance) VALUES (?, ?, 0)")) {
            statement.setString(1, String.valueOf(accountNumber));
            statement.setString(2, userName);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("계좌가 생성되었습니다.");
                System.out.println(userName + "님의 계좌번호는 " + accountNumber + "입니다.");
            } else {
                System.out.println("계좌 생성에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    // 계좌 삭제 기능
    public void deleteAccount(String accountNumber) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE account_number = ?")) {
            statement.setString(1, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("계좌가 삭제되었습니다.");
            } else {
                System.out.println("계좌 삭제에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

    }
}
