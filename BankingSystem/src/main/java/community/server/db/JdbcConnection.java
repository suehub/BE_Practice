package community.server.db;

import community.server.exceptions.GlobalExceptionConfig;

public class JdbcConnection {
  public JdbcConnection() {
    try{
      ConnectionFactory.getConnection();
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
  }


}
