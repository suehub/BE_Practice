package community.server.feature;

import community.server.db.ConnectionFactory;
import community.server.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountWithdrawal {

  private final Scanner scanner;
  private int accountNumber;
  private int balance;

  public AccountWithdrawal(Scanner scanner) {
    this.scanner = scanner;
    System.out.println("AccountWithdrawal created!");
  }

  public void run() {
    getAccountNumber();
    checkAccountExists();
    getBalance();
    withdrawAmount();
  }

  void getAccountNumber() {
    System.out.println("Please enter your account number: ");
  }

  void checkAccountExists() {
    String sql = "SELECT * FROM account WHERE account_num = ?";
    try (Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, accountNumber);
      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        System.out.println("Account not found");
        throw new RuntimeException("Account not found");
      }
      System.out.println("Account Number: " + resultSet.getString("account_num"));
    } catch (SQLException e) {
      GlobalExceptionConfig.log(e);
      throw new RuntimeException("Error checking account existence");
    }
  }

  void getBalance() {
    String sql = "SELECT amount FROM balance WHERE account_num = ?";
    try (Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, accountNumber);
      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        System.out.println("Balance not found");
        connection.rollback();
        throw new RuntimeException("Balance not found");
      }
      balance = resultSet.getInt("amount");
      System.out.println("Account Balance: " + balance);
    } catch (SQLException e) {
      GlobalExceptionConfig.log(e);
      throw new RuntimeException("Error getting account balance");
    }
  }

  void withdrawAmount() {
    System.out.println("Please enter the amount you want to withdraw: ");
    int withdrawalAmount = scanner.nextInt();
    if (withdrawalAmount > balance) {
      System.out.println("Insufficient balance");
      throw new RuntimeException("Insufficient balance");
    }

    String insertTransactionSql = "INSERT INTO transaction (account_num, amount, transaction_type) VALUES (?, ?, 'withdraw')";
    String updateBalanceSql = "UPDATE balance SET amount = amount - ? WHERE account_num = ?";

    try (Connection connection = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionSql);
        PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceSql)) {

      insertTransactionStatement.setInt(1, accountNumber);
      insertTransactionStatement.setInt(2, withdrawalAmount);
      insertTransactionStatement.executeUpdate();

      updateBalanceStatement.setInt(1, withdrawalAmount);
      updateBalanceStatement.setInt(2, accountNumber);
      int updatedRows = updateBalanceStatement.executeUpdate();
      if (updatedRows > 0) {
        connection.commit();
      } else {
        connection.rollback();
      }
    } catch (SQLException e) {
      GlobalExceptionConfig.log(e);
      throw new RuntimeException("Error executing withdrawal");
    }
  }
}