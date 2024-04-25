package community.server.db;

import community.server.exceptions.GlobalExceptionConfig;

public class JdbcConnection {
  public JdbcConnection() {
    try{
      ConnectionFactory connectionFactory = ConnectionFactory.INSTANCE;
      connectionFactory.getConnection();
    } catch (Exception e) {
      GlobalExceptionConfig.log(e);
    }
  }

}
