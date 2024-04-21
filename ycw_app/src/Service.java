import account.Account;
import account.AccountService;
import page.PagePrinter;
import page.Status;
import trade.Trade;
import trade.TradeService;
import user.User;
import user.UserService;

import javax.lang.model.type.ArrayType;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

    Scanner sc = new Scanner(System.in);

    Status serviceMenu (Status status){
        switch (status.getWorkName()) {
            case "log_in", "sign_up", "my_page"-> userService(status);
            case "manage_accounts", "open_account", "close_account" -> accountService(status);
            case "accountHistory", "deposit", "withrow", "transfer" -> tradeService(status);
            default -> throw new IllegalStateException("[Error] 잘못된 입력 입니다.");
        }
        return status;
    }


    Status userService (Status status) {
        UserService service = new UserService();
        User user = new User();
        boolean inputError = true;

        switch (status.getWorkName()){
            case "log_in" -> {
                if (status.getUserId() == "non_signed_guest"){
                    status = PagePrinter.checkUserPage(status);
                }

                if (status.getUserId() == "signed_guest") {
                    user = PagePrinter.loginPage();
                    try {
                        status = service.logIn(status, user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "sign_up" -> {
                do {
                    try {
                        user = PagePrinter.signUpPage();
                        status = service.signUp(status, user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (status.getMessage().contains("[Info]")) {
                        inputError = false;
                    } else if (status.getMessage().contains("[Error]")) {
                        PagePrinter.printMessage(status);
                    }
                } while (inputError);
            }
            case "my_page" -> {
                try {
                     user = service.selectOne(status.getUserId());
                     status = PagePrinter.myInfoPage(status, user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        PagePrinter.printMessage(status);
        PagePrinter.underPage(status);

        return status;
    }


    Status accountService(Status status) {
        AccountService service = new AccountService();

        switch (status.getWorkName()) {
            case "manage_accounts" -> {
                ArrayList<Account> accountList;
                try {
                     accountList = service.selectMyAllAccount(status);
                     status = PagePrinter.manageAccountPage(status, accountList);
                     if (status.getWorkName() == "open_account"){

                     } else if (status.getWorkName() == "close_account") {

                     }
                    // accountList = service.selectMyAccount();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case "open_account" -> {
                try {
                    status = service.insert(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case "close_account" -> {
                try {
                    status = service.delete(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        PagePrinter.printMessage(status);
        PagePrinter.underPage(status);

        return status;
    }


    Status tradeService(Status status) {
        Connection con = null;
        TradeService service = new TradeService();
        Trade trade;

        switch (status.getWorkName()) {
            case "accountHistory" -> {

            }

            case "deposit", "withrow" -> {
                // 입/출금 정보 생성
                trade = PagePrinter.inputTradeInfo(status);
                // 거래 insert 후 계좌 update 실행
                try {
                    status.setMessage(service.insert(trade));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case "transfer" -> {
                boolean inputError = true;
                // 송금정보 생성
                trade = PagePrinter.inputTradeInfo(status);
                do {
                    // 보내는/받는 계좌 및 송금액 입력
                    try {
                        status = service.transfer(status, trade);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    inputError = false;
                } while (inputError == true);
            }
        }
        PagePrinter.printMessage(status);
        PagePrinter.underPage(status);

        return status;
    }
}
