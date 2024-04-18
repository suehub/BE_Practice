package community.feature;

import community.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountTransaction {
  private final Scanner sc;
  private int accountNum;
  private int transactAmount;
  private int transactTarget;
  public AccountTransaction(Scanner sc) {
    this.sc = sc;
    System.out.println("AccountTransaction created!");
    accountTransaction();
  }

  private void accountTransaction() {
    System.out.println("Please enter the account number you want to transact from: ");
    int accountNum = sc.nextInt();
    this.accountNum = accountNum;
    System.out.println("Account Number: " + accountNum);
    transactAmount();
  }

  private void transactAmount() {
    System.out.println("Please enter the amount you want to transact: ");
    Scanner sc = new Scanner(System.in);
    int transactAmount = sc.nextInt();
    this.transactAmount = transactAmount;
    System.out.println("Amount: " + transactAmount);
    transactTarget();
  }

  private void transactTarget() {
    System.out.println("Please enter the account number you want to transact to: ");
    Scanner sc = new Scanner(System.in);
    int transactTarget = sc.nextInt();
    this.transactTarget = transactTarget;
    System.out.println("Target Account Number: " + transactTarget);
    transactExecution();
  }

  private void transactExecution() {
    System.out.println("Transaction in progress...");
    Connection con = ConnectionFactory.getConnection();
    PreparedStatement ps;
    String from = "update transaction set amount = amount - ? where account_num = ? and"
        + " transaction_type = 'transaction'";
    String to = "update transaction set amount = amount + ? where account_num = ? and"
        + " transaction_type = 'transaction'";
    try{
      ps= con.prepareStatement(from);
      ps.setInt(1, transactAmount);
      ps.setInt(2, accountNum);
      ps.executeUpdate();
      ps= con.prepareStatement(to);
      ps.setInt(1, transactAmount);
      ps.setInt(2, transactTarget);
      ps.executeUpdate();


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Transaction completed!");


  }


}
