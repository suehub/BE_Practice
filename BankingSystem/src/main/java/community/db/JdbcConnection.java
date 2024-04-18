package community.db;

import community.exceptions.GlobalExceptionConfig;

public class JdbcConnection {
  public JdbcConnection() {
    try{
      ConnectionFactory.getConnection();
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
  }


}
