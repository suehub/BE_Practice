package community.Client;

import community.db.JdbcConnection;
import community.feature.AccountCreation;
import community.feature.AccountDeletion;
import community.feature.AccountDeposit;
import community.feature.AccountSearch;
import community.feature.AccountTransaction;
import community.feature.AccountWithdrawal;
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
        AccountSearch as = new AccountSearch(sc);
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