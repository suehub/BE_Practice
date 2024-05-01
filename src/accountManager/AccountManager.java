package accountManager;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.System.getenv;

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

    // 입금 기능
    public void deposit(String id, String accountNumber, double amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement depositStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance + ? WHERE account_number = ?");
             PreparedStatement transactionStatement = connection.prepareStatement("INSERT INTO Transactions (transaction_type, transaction_amount, transaction_date, deposit_account) VALUES (?, ?, NOW(), ?)")) {
            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, accountNumber);

            int rowsUpdated = depositStatement.executeUpdate();

            // Transaction 테이블에 거래 내역 저장
            transactionStatement.setString(1, "deposit");
            transactionStatement.setDouble(2, amount);
            transactionStatement.setString(3, accountNumber);

            int transactionRowsUpdated = transactionStatement.executeUpdate();

            if (rowsUpdated > 0 && transactionRowsUpdated > 0) {
                System.out.println("입금이 완료되었습니다.");
                double balance = checkBalance(connection, accountNumber);
                if (balance >= 0) {
                    System.out.printf("계좌 잔액: %.0f원 \n", balance);
                }

            } else {
                System.out.println("입금에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 출금 기능
    public void withdrawal(String id, String accountNumber, double amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement depositStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance - ? WHERE account_number = ?");
             PreparedStatement transactionStatement = connection.prepareStatement("INSERT INTO Transactions (transaction_type, transaction_amount, transaction_date, withdrawal_account) VALUES (?, ?, NOW(), ?)")) {
            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, accountNumber);

            int rowsUpdated = depositStatement.executeUpdate();

            // Transaction 테이블에 거래 내역 저장
            transactionStatement.setString(1, "withdrawal");
            transactionStatement.setDouble(2, amount);
            transactionStatement.setString(3, accountNumber);

            int transactionRowsUpdated = transactionStatement.executeUpdate();

            if (rowsUpdated > 0 && transactionRowsUpdated > 0) {
                System.out.println("출금이 완료되었습니다.");
                double balance = checkBalance(connection, accountNumber);
                if (balance >= 0) {
                    System.out.printf("계좌 잔액: %.0f원 \n", balance);
                }

            } else {
                System.out.println("출금에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 계좌 송금
    public void transfer(String withdrawalAccount, String depositAccount, double amount) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false); // 트랜잭션 시작

            // 송금자 계좌에서 출금
            PreparedStatement withdrawStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance - ? WHERE account_number = ?");
            withdrawStatement.setDouble(1, amount);
            withdrawStatement.setString(2, withdrawalAccount);
            int withdrawRowsUpdated = withdrawStatement.executeUpdate();

            // 수신자 계좌로 입금
            PreparedStatement depositStatement = connection.prepareStatement("UPDATE Accounts SET balance = balance + ? WHERE account_number = ?");
            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, depositAccount);
            int depositRowsUpdated = depositStatement.executeUpdate();

            // Transaction 테이블에 거래 내역 저장
            if (withdrawRowsUpdated > 0 && depositRowsUpdated > 0) {
                PreparedStatement transactionStatement = connection.prepareStatement("INSERT INTO Transactions (transaction_type, transaction_amount, transaction_date, deposit_account, withdrawal_account) VALUES (?, ?, NOW(), ?, ?)");
                transactionStatement.setString(1, "transfer");
                transactionStatement.setDouble(2, amount);
                transactionStatement.setString(3, depositAccount);
                transactionStatement.setString(4, withdrawalAccount);
                int transactionRowsUpdated = transactionStatement.executeUpdate();

                if (transactionRowsUpdated > 0) {
                    connection.commit(); // 트랜잭션 성공적으로 완료
                    System.out.println("송금이 완료되었습니다.");
                    double transferBalance = checkBalance(connection, withdrawalAccount);
                    if (transferBalance >= 0) {
                        System.out.printf("송금 후 %s 계좌 잔액: %.0f원\n", withdrawalAccount, transferBalance);
                    }
                } else {
                    throw new SQLException("거래 내역 저장에 실패했습니다.");
                }
            } else {
                throw new SQLException("출금 또는 입금에 실패했습니다.");
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // 롤백
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // 트랜잭션 종료
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }


    // 계좌 생성 기능
    public void createAccount(String userName) {
        Random rand = new Random();
        int accountNumber = rand.nextInt(1000000000);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts(account_number, balance, user_id) VALUES (?, ?, 0)")) {
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
