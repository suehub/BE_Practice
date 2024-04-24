package community.Client;

import community.server.db.JdbcConnection;
import community.server.feature.AccountCreation;
import community.server.feature.AccountDeletion;
import community.server.feature.AccountDeposit;
import community.server.feature.AccountSearch;
import community.server.feature.AccountTransaction;
import community.server.feature.AccountWithdrawal;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    new JdbcConnection();
    ShowMenu showMenu = new ShowMenu();
    showMenu.showMenu();
    int choice = ShowMenu.getSelection();
    switch(choice){
      case 1 -> {
        AccountSearch as = new AccountSearch();
        as.run();
      }
      case 2 ->{
        AccountDeposit ad = new AccountDeposit(sc);
        ad.run();
      }
      case 3 -> {
        AccountWithdrawal aw = new AccountWithdrawal(sc);
        aw.run();
      }
      case 4 -> {
        AccountTransaction at = new AccountTransaction(sc);
        at.run();
      }
      case 5 -> {
        AccountCreation ac = new AccountCreation(sc);
        ac.run();
      }
      case 6 -> {
        AccountDeletion ad = new AccountDeletion(sc);
        ad.run();
      }
      case 7 -> System.exit(0);
    }
  }
}