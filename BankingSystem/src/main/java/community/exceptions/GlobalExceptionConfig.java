package community.exceptions;

public class GlobalExceptionConfig {

  public static void log(Exception e) {
    System.out.println("Logging exception: " + e);
  }
}
