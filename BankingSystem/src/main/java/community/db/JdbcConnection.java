package community.db;

import community.exceptions.GlobalExceptionConfig;
import java.sql.Connection;

public class JdbcConnection {
  public JdbcConnection() {
    System.out.println("JdbcConnection created");
    Connection con = null;

    try{
      con = ConnectionFactory.getConnection();
      System.out.println(con);
      System.out.println("Connection object created");
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    } finally {
      try {
        con.close();
        System.out.println("Connection closed");
      } catch (Exception e) {
        GlobalExceptionConfig.log(e);
      }
    }

  }


}
