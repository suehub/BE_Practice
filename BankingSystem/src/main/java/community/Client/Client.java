package community.Client;

import community.db.JdbcConnection;
import community.feature.AccountCreation;
import community.feature.AccountDeletion;
import community.feature.AccountDeposit;
import community.feature.AccountSearch;
import community.feature.AccountTransaction;
import community.feature.AccountWithdrawl;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    new JdbcConnection();
    new ShowMenu();
    int choice = ShowMenu.getSelection();
    switch(choice){
      case 1 -> new AccountSearch(sc);
      case 2 ->{
        AccountDeposit ad = new AccountDeposit(sc);
        ad.run();
      }
      case 3 -> new AccountWithdrawl(sc);
      case 4 -> new AccountTransaction(sc);
      case 5 -> new AccountCreation(sc);
      case 6 -> new AccountDeletion(sc);
      case 7 -> System.exit(0);
    }
  }
}