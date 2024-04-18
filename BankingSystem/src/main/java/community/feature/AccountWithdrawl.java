package community.feature;

import community.db.ConnectionFactory;
import community.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Scanner;

public class AccountWithdrawl {
  private final Scanner sc;

  private int accountNum;
  private String withdrawlSql;
  private int remains;

  public AccountWithdrawl(Scanner sc) {
    this.sc = sc;
    System.out.println("AccountWithdrawl created!");
  }
  public void run(){
    accountCheck();
    balanceCheck();
    balanceSet();
    withdrawAmount();
  }

  void accountCheck() {
    System.out.println("please enter your account number: ");
    PreparedStatement ps;
    ResultSet rs;
    String sql = "SELECT * FROM account WHERE account_num = ?";
    this.accountNum= sc.nextInt();
    try {
      Connection con = ConnectionFactory.getConnection();
      ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setInt(1, accountNum);
      rs = ps.executeQuery();
      if (!rs.next()) {
        System.out.println("Account not found");
        return ;
      }
      System.out.println("Account Number: " + rs.getString("account_num"));
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
  }
  private boolean balanceCheck(){
    PreparedStatement ps;
    ResultSet rs;
    String sql = "SELECT * FROM balance WHERE account_num = ?";
    try {
      Connection con = ConnectionFactory.getConnection();
      ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setInt(1, accountNum);
      rs = ps.executeQuery();
      if (!rs.next()) {
        System.out.println("balance not found");
        return false;
      }
      System.out.println("Account Number: " + rs.getString("account_num"));
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
    return true;
  }
  private boolean balanceSet(){
    PreparedStatement ps;
    ResultSet rs;
    String sql = "SELECT * FROM balance WHERE account_num = ?";
    try {
      Connection con = ConnectionFactory.getConnection();
      ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setInt(1, accountNum);
      rs = ps.executeQuery();
      if (!rs.next()) {
        System.out.println("balance not found");
        this.remains = rs.getInt("amount");
        return false;
      }
      System.out.println("Account Number: " + rs.getString("account_num"));
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
    return true;
  }

  private void withdrawAmount() {
    System.out.println("Please enter the amount you want to withdraw: ");
    int withdrawAmount = sc.nextInt();
    Connection con = ConnectionFactory.getConnection();
    PreparedStatement ps;
    if(remains < withdrawAmount){
      throw new RuntimeException("Not enough balance");
    }
    if(balanceCheck()){
      this.withdrawlSql = "update balance set amount = amount - ? where account_num = ?";
    }else{
      throw new RuntimeException("Balance not found");

    }
    String sql = "insert into transaction (account_num, amount, transaction_type) values (?, ?, 'withdraw')";
    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, withdrawAmount);
      ps.setInt(2, 1);
      ResultSet result = ps.executeQuery();
      while(result.next()){
        System.out.println("Amount: " + result.getString("amount"));
      }
      ps = con.prepareStatement(withdrawlSql);
      ps.setInt(1, withdrawAmount);
      ps.setInt(2, accountNum);
      int answer = ps.executeUpdate();
      if(answer > 0){
        System.out.println("Amount withdrawn successfully");
      }

    } catch (Exception e) {
      System.out.println("Error: " + e);
    }

  }

}
