package community.Client;

import community.server.domain.account.AccountCreation;
import community.server.domain.account.AccountDeletion;
import community.server.domain.account.AccountDeposit;
import community.server.domain.account.AccountTransaction;
import community.server.domain.account.AccountWithdrawal;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    executorService.execute(Main::run);
    executorService.shutdown();
  }

  private static void run() {
    Scanner sc = new Scanner(System.in);
    ShowMenu showMenu = new ShowMenu();
    showMenu.showMenu();
    int choice = ShowMenu.getSelection();
    switch(choice){
      case 1 ->{
        AccountDeposit ad = new AccountDeposit(sc);
        ad.run();
      }
      case 2 -> {
        AccountWithdrawal aw = new AccountWithdrawal(sc);
        aw.run();
      }
      case 3 -> {
        AccountTransaction at = new AccountTransaction(sc);
        at.run();
      }
      case 4 -> {
        AccountCreation ac = new AccountCreation(sc);
        ac.run();
      }
      case 5 -> {
        AccountDeletion ad = new AccountDeletion(sc);
        ad.run();
      }
      case 6 -> System.exit(0);
    }
  }
}