package community.server.exceptions;

import java.util.logging.Logger;

public class GlobalExceptionConfig {
  private static final Logger LOGGER = Logger.getLogger(GlobalExceptionConfig.class.getName());
  public static void log(Exception e) {
    LOGGER.severe(e.getMessage());
  }
}
