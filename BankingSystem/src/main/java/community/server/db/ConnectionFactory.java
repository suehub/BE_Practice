package community.server.db;

import community.server.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
  public static Connection getConnection() {
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_system", "root", "root");
      if(conn == null){
        GlobalExceptionConfig.log(new Exception("Connection failed"));
        System.exit(-1);
      }
      return conn;
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
    return null;
  }

}
