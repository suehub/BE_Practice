package community.server.db;

import community.server.exceptions.GlobalExceptionConfig;
import java.sql.Connection;
import java.sql.DriverManager;

public enum ConnectionFactory {
  INSTANCE;
  private static final int MAX_ATTEMPTS =5;
  private static final long INITIAL_BACKOFF=1000;
  private int attempts = 0;


  public Connection getConnection() {
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_system", "root", "root");
      if(conn == null){
        GlobalExceptionConfig.log(new Exception("Connection failed"));
        System.exit(-1);
      }
      // Disable auto-commit
      conn.setAutoCommit(false);
      return conn;
    } catch (Exception e) {
      attempts++;
      if(attempts> MAX_ATTEMPTS){
        GlobalExceptionConfig.log(e);
      };
      try{
        long backoff = INITIAL_BACKOFF * (long) Math.pow(2, attempts);
        System.out.println("Retrying in " + backoff + " milliseconds");
        Thread.sleep(backoff);
      }catch (InterruptedException ex){
        Thread.currentThread().interrupt();
        GlobalExceptionConfig.log(ex);
      }
    }
    return null;
  }

}
