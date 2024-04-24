package community.server.feature;

import community.server.db.ConnectionFactory;
import community.server.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountTransaction {
  private final Scanner scanner;

  private int sourceAccountNumber;
  private int transactionAmount;
  private int targetAccountNumber;

  public AccountTransaction(Scanner scanner) {
    this.scanner = scanner;
    System.out.println("AccountTransaction created!");
  }

  public void run() {
    setSourceAccountNumber(scanner.nextInt());
    setTargetAccountNumber(scanner.nextInt());
    setTransactionAmount(scanner.nextInt());
    executeTransaction();
  }
  void setTransactionAmount(int transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  void setSourceAccountNumber(int sourceAccountNumber) {
    this.sourceAccountNumber = sourceAccountNumber;
  }
  void setTargetAccountNumber(int targetAccountNumber) {
    this.targetAccountNumber = targetAccountNumber;
  }


  private void executeTransaction() {
    System.out.println("Transaction in progress...");

    try (Connection connection = ConnectionFactory.getConnection()) {
      connection.setAutoCommit(false);

      checkBalance(connection, sourceAccountNumber, transactionAmount);
      executeWithdrawal(connection, sourceAccountNumber, transactionAmount);
      executeDeposit(connection, targetAccountNumber, transactionAmount);

      connection.commit();
      System.out.println("Transaction completed!");
    } catch (SQLException e) {
      GlobalExceptionConfig.log(e);
      System.out.println("Transaction failed. Please try again later.");
    }
  }

  void checkBalance(Connection connection, int accountNumber, int amount) throws SQLException {
    String balanceQuery = "SELECT amount FROM balance WHERE account_num = ?";
    try (PreparedStatement balanceStatement = connection.prepareStatement(balanceQuery)) {
      balanceStatement.setInt(1, accountNumber);
      ResultSet resultSet = balanceStatement.executeQuery();
      if (!resultSet.next() || resultSet.getInt("amount") < amount) {
        throw new RuntimeException("Insufficient balance");
      }
    }
  }

  private void executeWithdrawal(Connection connection, int accountNumber, int amount) throws SQLException {
    String withdrawalQuery = "INSERT INTO transaction (account_num, amount, transaction_type) VALUES (?, ?, 'withdraw')";
    String updateBalanceQuery = "UPDATE balance SET amount = amount - ? WHERE account_num = ?";
    executeTransactionQuery(connection, withdrawalQuery, updateBalanceQuery, accountNumber, amount);
  }

  private void executeDeposit(Connection connection, int accountNumber, int amount) throws SQLException {
    String depositQuery = "INSERT INTO transaction (account_num, amount, transaction_type) VALUES (?, ?, 'deposit')";
    String updateBalanceQuery = "UPDATE balance SET amount = amount + ? WHERE account_num = ?";
    executeTransactionQuery(connection, depositQuery, updateBalanceQuery, accountNumber, amount);
  }

  private void executeTransactionQuery(Connection connection, String transactionQuery, String updateBalanceQuery,
      int accountNumber, int amount) throws SQLException {
    try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery);
        PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceQuery)) {
      transactionStatement.setInt(1, accountNumber);
      transactionStatement.setInt(2, amount);
      transactionStatement.executeUpdate();

      updateBalanceStatement.setInt(1, amount);
      updateBalanceStatement.setInt(2, accountNumber);
      updateBalanceStatement.executeUpdate();
    }
  }
}