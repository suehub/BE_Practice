package community.server.domain.account;

import community.server.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Scanner;

public class AccountDeletion extends AccountCheck implements Account {
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
    String sql = "delete from account where account_num = ?";
    try(Connection con = ConnectionFactory.INSTANCE.getConnection();
        PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
    ) {
      ps.setInt(1, accountNum);
      int result = ps.executeUpdate();
      if (result <= 0) {
        con.rollback();
        System.out.println("Account deleted failed");
      }
      con.commit();
    } catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }
}
