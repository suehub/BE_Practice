package community.server.feature;

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
    String sql = "SELECT * FROM account WHERE account_num = ?";
    int accountNum= sc.nextInt();
    try (Connection con = ConnectionFactory.INSTANCE.getConnection()){
      PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setInt(1, accountNum);
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
