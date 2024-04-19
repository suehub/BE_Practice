package page;

import account.Account;
import trade.Trade;
import user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PagePrinter {

    static Scanner sc = new Scanner(System.in);

    public static int mainPage() {
        String page = "";
        page += "----------------------------------\n";
        page += "             뱅킹 시스템            \n";
        page += "----------------------------------\n";
        page += "  1. My page\n";
        page += "  2. 계좌 내역\n";
        page += "  3. 계좌 관리\n";
        page += "  5. 입금/출금\n";
        page += "  6. 송금\n";
        page += "----------------------------------\n";
        page += "  0. 종료\n";
        page += "----------------------------------\n";
        page += "[input] \n";
        System.out.println(page);
        int selectNum = sc.nextInt();
        if (selectNum < 0 || selectNum > 8) {
            System.out.println("[Error] 1. ~ 8.  사이로 입력해주세요");
            return 0; // 0 받을 시 루프 처리
        }
        return selectNum;
    }

    public static User loginPage(){
        User user = new User();
        String page = "";
        page += "----------------------------------\n";
        page += "              log-in\n";
        page += "----------------------------------\n";
        page += "- ID: ";
        System.out.print(page);
        user.setuserId(sc.next());
        System.out.print("- PW: ");
        user.setPassword(sc.next());

       return user;
    }

    public static Trade inputTradeInfo(int selectNum) {
        Trade trade = new Trade();
        switch (selectNum) {
            case 6 -> trade.setAction("입금");
            case 7 -> trade.setAction("출금");
            case 8 -> trade.setAction("송금");
        }
        System.out.println("----------------------------------");
        System.out.println("          "+ trade.getAction() +" 정보 입력");
        System.out.println("----------------------------------");
        System.out.print("거래번호: ");
        trade.setTradeId(sc.nextInt());
        System.out.print("신청자 ID: ");
        trade.setUserId(sc.next());
        switch (trade.getAction()) {
            case "입금", "출금" -> {
                System.out.print( trade.getAction() + " 계좌: ");
                trade.setRequestAccount("-");
            }
            case "송금" -> {
                System.out.print("출금 계좌: ");
                trade.setRequestAccount(sc.next());
                System.out.print("송금 계좌: ");
            }
        }
        trade.setTargetAccount(sc.next());
        System.out.print(trade.getAction() + " 금액: ");
        trade.setAmount(sc.nextInt());
        System.out.println("----------------------------------");
        return trade;
    }

    void closeMessage (String resultMessage) {
        String page = "";
        page += "----------------------------------\n";
        page +="[Info] 아무 키나 입력하여 계속\n";
        System.out.println(resultMessage);
        System.out.println(page);
        String a = sc.next();
    }
}
