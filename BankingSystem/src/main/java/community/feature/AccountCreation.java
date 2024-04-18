package community.feature;

import community.db.ConnectionFactory;
import community.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class AccountCreation {
  private final Scanner sc;
  public AccountCreation(Scanner sc){
    this.sc = sc;
    System.out.println("AccountCreation created");
    System.out.println("please enter the details to create an account");
    System.out.println("what is your name? :");
    AccountCreationDB();
  }

  private void AccountCreationDB() {
    String accountNum = sc.nextLine();
    LocalDateTime accountOpen = LocalDateTime.now();
    int AccountType = 1;

    Connection con = ConnectionFactory.getConnection();
    PreparedStatement ps;
    String sql;
    sql = "insert into account (account_num, account_open, account_type) values (?,?,?)";
    try{
      ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setString(1, accountNum);
      ps.setTimestamp(2, Timestamp.valueOf(accountOpen));
      ps.setInt(3, AccountType);
      int result = ps.executeUpdate();
      if(result > 0){
        System.out.println("Account created successfully");
      }
    }catch(Exception e){
      GlobalExceptionConfig.log(e);
    }

  }

}
