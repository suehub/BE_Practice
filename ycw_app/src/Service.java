import account.Account;
import account.AccountService;
import client.Pages;
import client.Status;
import trade.Trade;
import trade.TradeService;
import user.User;
import user.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {

    Status serviceMenu (Status status){

        switch (status.getWorkName()) {
            case "log_in", "sign_up", "my_page" -> userService(status);
            case "manage_accounts", "open_account", "close_account" -> accountService(status);
            case "account_history", "deposit", "withdraw", "transfer" -> tradeService(status);
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
                if (status.getUserId().equals("non_signed_guest")){
                    status = Pages.checkUserPage(status);
                }

                if (status.getUserId().equals("signed_guest")) {
                    user = Pages.loginPage();
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
                        user = Pages.signUpPage();
                        status = service.signUp(status, user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (status.getMessage().contains("[Info]")) {
                        inputError = false;
                    } else if (status.getMessage().contains("[Error]")) {
                        Pages.printMessage(status);
                    }
                } while (inputError);
            }
            case "my_page" -> {
                try {
                     user = service.selectOne(status.getUserId());
                     status = Pages.myInfoPage(status, user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Pages.printMessage(status);
        Pages.underPage(status);

        return status;
    }


    Status accountService(Status status) {
        AccountService service = new AccountService();

        switch (status.getWorkName()) {
            case "manage_accounts" -> {
                ArrayList<Account> accountList;
                try {
                     accountList = service.selectMyAllAccount(status);
                     status = Pages.manageAccountPage(status, accountList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case "open_account" -> {
                try {
                    status = Pages.openAccountPage(status);
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
        Pages.printMessage(status);
        Pages.underPage(status);

        return status;
    }


    Status tradeService(Status status) {
        Connection con = null;
        TradeService service = new TradeService();
        Trade trade;

        switch (status.getWorkName()) {

            case "account_history" -> {
                ArrayList<Trade> tradeList;
                try {
                    tradeList = service.selectAccountHistory(status);
                    status = Pages.accountHistoryPage(status, tradeList);

                } catch (SQLException e) {

                }
            }
            case "deposit", "withdraw" -> {
                System.out.println(this.getClass() + " / " + status.toString());
                AccountService accountService = new AccountService();
                ArrayList<Account> accountList = new ArrayList<>();

                if (status.getWorkName().equals("deposit")) {
                    status = Pages.checkDepoistToOtherPage(status);
                }

                if (status.getWorkName().equals("withdraw") ||
                    (status.getWorkName().equals("deposit") && status.getData().equals("my_account"))){
                    try {
                        accountList = accountService.selectMyAllAccount(status);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    status = Pages.selectAccountPage(status, accountList);
                }

                if (!status.getMessage().contains("[Error]") && !status.getMessage().contains("취소")) {
                    // 입/출금 정보 생성
                    trade = Pages.inputTradeInfo(status);
                    // 거래 insert 후 계좌 update 실행
                    try {
                        status = service.insert(status, trade);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "transfer" -> {
                System.out.println(this.getClass() + " / " + status.toString());
                AccountService accountService = new AccountService();
                ArrayList<Account> accountList = new ArrayList<>();
                boolean inputError = true;

                try {
                    accountList = accountService.selectMyAllAccount(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                status = Pages.selectAccountPage(status, accountList);

                if (!status.getMessage().contains("[Error]") && !status.getMessage().contains("취소")) {
                    // 송금정보 생성
                    trade = Pages.inputTradeInfo(status);

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
        }
        Pages.printMessage(status);
        Pages.underPage(status);

        return status;
    }
}
