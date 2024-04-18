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

        switch (selectNum){
            case 1 -> {
                String result;
                try {
                    result = service.insert();
                    System.out.println(result);
                    System.out.print("[Info] 아무 키나 입력하여 계속");
                    String a = sc.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                ArrayList<User> list = new ArrayList<>();

                try {
                    list = service.selectOne();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for(User user: list) {
                    System.out.println("----------------------------------");
                    System.out.println("          사용자 조회 결과");
                    System.out.println("----------------------------------");
                    System.out.println(user.toString());
                    System.out.println("----------------------------------");
                    System.out.print("[Info] 아무 키나 입력하여 계속");
                    String a = sc.next();
                }
            }
        }
    }

    void accountService(int selectNum) {
        AccountService service = new AccountService();

        switch (selectNum) {
            case 3 -> {
                String result;
                try {
                    result = service.insert();
                    System.out.println(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> {
                String result;
                ArrayList<Account> list = new ArrayList<>();

                System.out.println("----------------------------------");
                System.out.println("             조회 메뉴              ");
                System.out.println("----------------------------------");
                System.out.println("1. 계좌번호로 조회");
                System.out.println("2. 내 계좌 전체 조회");
                int inputNum;
                do {
                    inputNum = sc.nextInt();
                    if (inputNum != 1 && inputNum != 2) {
                        System.out.println("잘못된 입력입니다.");
                    }
                } while (inputNum != 1 && inputNum != 2);

                try {
                    if (inputNum == 1) {
                        list = service.selectOne();
                    } else if (inputNum == 2) {
                        list = service.selectAll();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (list.isEmpty()) {
                    System.out.println("존재하지 않는 계좌입니다.");
                } else if (!list.isEmpty()) {
                    for (Account account : list) {
                        System.out.println("----------------------------------");
                        System.out.println("          계좌 조회 결과");
                        System.out.println("----------------------------------");
                        System.out.println(account.toString());
                        System.out.println();
                    }
                }
                System.out.print("[Info] 아무 키나 입력하여 계속");
                String a = sc.next();

            }
            case 5 -> {
                String result;
                try {
                    result = service.delete();
                    System.out.println(result);
                    System.out.println("----------------------------------");
                    System.out.println("[Info] 아무 키나 입력하여 계속");
                    String a = sc.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    void tradeService(int selectNum) {
        TradeService service = new TradeService();

        switch (selectNum) {
            case 6, 7 -> {
                String result;
                try {
                    result = service.insert(selectNum);
                    System.out.println(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 8 -> {

            }
        }
    }

}
