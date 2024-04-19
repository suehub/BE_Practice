import account.Account;
import account.AccountService;
import page.PagePrinter;
import page.Status;
import trade.TradeService;
import user.User;
import user.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

    Scanner sc = new Scanner(System.in);

    void serviceMenu (Status status){
        switch (status.getWorkNum()) {
            case 0 -> loginService(status);
            case 1 -> userService(status.getWorkNum());
            case 3, 4, 5  -> accountService(status.getWorkNum());
            case 6, 7, 8 -> tradeService(status.getWorkNum());
            default -> throw new IllegalStateException("[Error] 잘못된 입력 입니다.");
        }
    }

    void loginService(Status status) {
        UserService service = new UserService();
        User user = PagePrinter.loginPage();
        try {
            service.logIn(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void userService (int workNum) {
        UserService service = new UserService();
        String resultMessage = "";

        switch (workNum){
            case 1 -> {
                try {
                    resultMessage = service.insert();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                try {
                    resultMessage = service.selectOne("");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void accountService(int workNum) {
        AccountService service = new AccountService();
        ArrayList<Account> accountList;
        String resultMessage = "";

        switch (workNum) {
            case 3 -> { // 계좌 개설
                try {
                    resultMessage = service.insert();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> { //

                try {
                    resultMessage = service.selectMyAllAccount();
                    System.out.println(resultMessage);
                    System.out.println("[info] 거래내역 ");
                    accountList = service.selectMyAccount();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 5 -> {
                try {
                    resultMessage = service.delete();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + workNum);
        }
    }

    void tradeService(int workNum) {
        TradeService service = new TradeService();
        String resultMessage = "";

        switch (workNum) {
            case 6, 7 -> {
                try {
                    resultMessage = service.insert(workNum);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 8 -> {
                try {
                    resultMessage = service.transfer(workNum);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
