import account.Account;
import account.AccountService;
import client.Pages;
import client.Status;
import trade.Trade;
import trade.TradeService;
import user.User;
import user.UserService;
import controller.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {

    Status serviceMenu (Status status){
        status.setWorkFlow(Flow.RUN);

        switch (status.getWorkTag()) {
            case LOG_IN, SIGN_UP, MY_PAGE -> userService(status);
            case MANAGE_ACCOUNTS, OPEN_ACCOUNT, CLOSE_ACCOUNT -> accountService(status);
            case ACCOUNT_HISTORY, DEPOSIT, WITHDRAW, TRANSFER -> tradeService(status);
            default -> throw new IllegalStateException("[Error] 잘못된 입력 입니다.");
        }
        return status;
    }


    Status userService (Status status) {
        UserService service = new UserService();
        User user = new User();
        boolean inputError = true;

        switch (status.getWorkTag()){
            case LOG_IN -> {
            if (status.getUserId().equals(Flow.NEW_GUEST.getFlow())){
                    status = Pages.checkUserPage(status);
                }

                if (status.getUserId().equals(Flow.OLD_GUEST.getFlow())) {
                    user = Pages.loginPage();
                    try {
                        status = service.logIn(status, user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            case SIGN_UP -> {
                do {
                    try {
                        user = Pages.signUpPage();
                        status = service.signUp(status, user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (status.getMessage().contains(Message.INFO.getMessage())) {
                        inputError = false;
                    } else if (status.getMessage().contains(Message.ERROR.getMessage())) {
                        Pages.printMessage(status);
                    }
                } while (inputError);
            }
            case MY_PAGE -> {
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

        switch (status.getWorkTag()) {
            case MANAGE_ACCOUNTS -> {
                ArrayList<Account> accountList;
                try {
                     accountList = service.selectMyAllAccount(status);
                     status = Pages.manageAccountPage(status, accountList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case OPEN_ACCOUNT -> {
                try {
                    status = Pages.openAccountPage(status);
                    status = service.insert(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case CLOSE_ACCOUNT -> {
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

        switch (status.getWorkTag()) {

            case ACCOUNT_HISTORY -> {
                ArrayList<Trade> tradeList;
                try {
                    tradeList = service.selectAccountHistory(status);
                    status = Pages.accountHistoryPage(status, tradeList);

                } catch (SQLException e) {

                }
            }
            case DEPOSIT, WITHDRAW -> {
                AccountService accountService = new AccountService();
                ArrayList<Account> accountList = new ArrayList<>();

                System.out.println(status.toString());
                if (status.getWorkTag().equals(Tag.DEPOSIT) && !status.getData().equals(Tag.MY_ACCOUNT.getTag())) {
                    status = Pages.checkDepoistToOtherPage(status);
                }

                if (status.getWorkTag().equals(Tag.WITHDRAW) ||
                    (status.getWorkTag().equals(Tag.DEPOSIT) && status.getData().equals(Tag.MY_ACCOUNT.getTag()))){
                    try {
                        accountList = accountService.selectMyAllAccount(status);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    status = Pages.selectAccountPage(status, accountList);
                }

                if (!status.getMessage().contains(Message.ERROR.getMessage())
                    && !status.getMessage().contains(Message.CANCLE.getMessage())) {
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
            case TRANSFER -> {
                AccountService accountService = new AccountService();
                ArrayList<Account> accountList = new ArrayList<>();
                boolean inputError = true;

                try {
                    accountList = accountService.selectMyAllAccount(status);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                status = Pages.selectAccountPage(status, accountList);

                if (!status.getMessage().contains(Message.ERROR.getMessage())
                    && !status.getMessage().contains(Message.CANCLE.getMessage())) {
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
