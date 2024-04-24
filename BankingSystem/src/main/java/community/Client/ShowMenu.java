package community.Client;

import community.server.exceptions.GlobalExceptionConfig;
import java.util.Scanner;

public class ShowMenu {
  public ShowMenu(){

  }
  public void showMenu(){
    System.out.println("Welcome to the Banking System");
    System.out.println("1. Search Account");
    System.out.println("2. Deposit");
    System.out.println("3. Withdraw");
    System.out.println("4. Transfer");
    System.out.println("5. Account Creation");
    System.out.println("6. Account Deletion");
    System.out.println("7. Exit");
  }

  public static int getSelection() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter your choice: ");
    try{
      return sc.nextInt();
    }catch(Exception e){
      GlobalExceptionConfig.log(e);
      return 0;

    }
  }
}
