package community.server.domain.account;

import community.server.db.ConnectionFactory;
import community.server.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Scanner;

public abstract class AccountCheck {
  public boolean accountCheck() {
    Scanner sc = new Scanner(System.in);
    System.out.println("please enter your account number: ");
    String accountQuery = "SELECT * FROM account WHERE account_num = ?";
    return searchTx(sc, accountQuery);
  }
  public boolean balanceCheck(){
    Scanner sc = new Scanner(System.in);
    System.out.println("please enter your account number: ");
    String balanceQuery = "SELECT * FROM balance WHERE account_num = ?";
    return searchTx(sc, balanceQuery);
  }

  private boolean searchTx(Scanner sc, String balanceQuery) {
    String accountNum= sc.nextLine();

    try (Connection con = ConnectionFactory.INSTANCE.getConnection()){
      PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(balanceQuery);
      ps.setString(1, accountNum);
      if (!ps.executeQuery().next()) {
        con.rollback();
        return false;
      }
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
    return true;
  }
}
