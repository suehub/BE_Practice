import account.Account;
import account.AccountService;
import trade.Trade;
import trade.TradeService;
import user.User;
import user.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

    Scanner sc = new Scanner(System.in);

    void serviceMenu (int selectNum){
        switch (selectNum) {
            case 1, 2 -> userService(selectNum);
            case 3, 4, 5  -> accountService(selectNum);
            case 6, 7, 8 -> tradeService(selectNum);
            default -> throw new IllegalStateException("[Error] 잘못된 입력 입니다.");
        }
    }

    void userService (int selectNum){
        UserService service = new UserService();
        String resultMessage = "";

        switch (selectNum){
            case 1 -> {
                try {
                    resultMessage = service.insert();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                try {
                    resultMessage = service.selectOne();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                outputMessage(resultMessage);
            }
        }
    }

    void accountService(int selectNum) {
        AccountService service = new AccountService();
        ArrayList<Account> accountList;
        String resultMessage = "";
        int workNum = 0;

        switch (selectNum) {
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
            default -> throw new IllegalStateException("Unexpected value: " + selectNum);
        }
        outputMessage(resultMessage);
    }

    void tradeService(int selectNum) {
        TradeService service = new TradeService();
        String resultMessage = "";

        switch (selectNum) {
            case 6, 7 -> {
                try {
                    resultMessage = service.insert(selectNum);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 8 -> {
                try {
                    resultMessage = service.transfer(selectNum);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    int selectWork() {
        System.out.println("----------------------------------");
        System.out.println("             조회 메뉴              ");
        System.out.println("----------------------------------");
        System.out.println("1. 계좌번호로 조회");
        System.out.println("2. 내 계좌 전체 조회");
        int workNum;
        do {
            workNum = sc.nextInt();
            if (workNum != 1 && workNum != 2) {
                System.out.println("잘못된 입력입니다.");
            }
        } while (workNum != 1 && workNum != 2);

        return workNum;
    }

    void outputMessage (String resultMessage) {
        System.out.println(resultMessage);
        System.out.println("----------------------------------");
        System.out.println("[Info] 아무 키나 입력하여 계속");
        String a = sc.next();
    }
}
