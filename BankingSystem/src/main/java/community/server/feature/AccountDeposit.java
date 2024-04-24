package community.server.feature;

import community.server.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class AccountDeposit extends AccountCheck{
  private final Scanner sc;

  private int accountNum;

  public AccountDeposit(Scanner sc) {
    this.sc = sc;
    System.out.println("AccountDeposit created");
    System.out.println("Welcome to the run page");
  }
  public void run() {
    accountCheck();
    depositAmount();
  }



  private void depositAmount() {
    System.out.println("please enter the amount you want to run: ");
    Scanner sc = new Scanner(System.in);
    int depositAmount = sc.nextInt();
    PreparedStatement ps;
    String transactionInsert = "insert into transaction (account_num, amount, transaction_type) values (?, ?, 'run')";
    try (Connection con = ConnectionFactory.getConnection()){
      ps = Objects.requireNonNull(con).prepareStatement(transactionInsert);
      ps.setInt(1, accountNum);
      ps.setInt(2, depositAmount);
      int result = ps.executeUpdate();
      if (result > 0) {
        System.out.println("Amount deposited successfully");
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
