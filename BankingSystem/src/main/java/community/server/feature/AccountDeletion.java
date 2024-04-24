package community.server.feature;

import community.server.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Scanner;

public class AccountDeletion extends AccountCheck{
  private final Scanner sc;

  public AccountDeletion(Scanner sc) {
    this.sc = sc;
    System.out.println("AccountDeletion created");
  }
  public void run(){
    deleteAccount();
  }


  private void deleteAccount() {
    System.out.println("Please enter the account number you want to delete: ");
    int accountNum = sc.nextInt();
    if(!accountCheck()){
      System.out.println("Account does not exist");
      return;
    }
    PreparedStatement ps;
    String sql = "delete from account where account_num = ?";
    try(Connection con = ConnectionFactory.getConnection()) {
      ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setInt(1, accountNum);
      int result = ps.executeUpdate();
      if (result > 0) {
        System.out.println("Account deleted successfully");
      }
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }
}
