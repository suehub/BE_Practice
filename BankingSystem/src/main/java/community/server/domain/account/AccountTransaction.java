package community.server.domain.account;

import community.server.db.ConnectionFactory;
import community.server.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountTransaction extends AccountCheck implements Account {
  private final Scanner scanner;

  private int srcAccountNum;
  private int txAmount;
  private int tgtAccountNum;

  public AccountTransaction(Scanner scanner) {
    this.scanner = scanner;
    System.out.println("AccountTransaction created!");
  }

  public void run() {
    setSrcAccountNum(scanner.nextInt());
    setTgtAccountNum(scanner.nextInt());
    setTxAmount(scanner.nextInt());
    executeTransaction();
  }
  void setTxAmount(int txAmount) {
    this.txAmount = txAmount;
  }

  void setSrcAccountNum(int srcAccountNum) {
    this.srcAccountNum = srcAccountNum;
  }
  void setTgtAccountNum(int tgtAccountNum) {
    this.tgtAccountNum = tgtAccountNum;
  }


  private void executeTransaction() {
    System.out.println("Transaction in progress...");

    try (Connection conn = ConnectionFactory.INSTANCE.getConnection()) {
      conn.setAutoCommit(false);
      if(!balanceCheck()){
        System.out.println("Insufficient balance");
        conn.rollback();
        return;
      }
      executeWithdrawal(conn, srcAccountNum, txAmount);

      executeDeposit(conn, tgtAccountNum, txAmount);
     
      conn.commit();
      System.out.println("Transaction completed!");
    } catch (SQLException e) {
      GlobalExceptionConfig.log(e);
      System.out.println("Transaction failed. Please try again later.");
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
    } catch (SQLException e) {
      connection.rollback();
      throw e;
    }
  }
}