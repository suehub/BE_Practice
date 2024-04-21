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

    public static Status mainPage(Status status) {
        String page = "";
        int workNum;

        page += "----------------------------------\n";
        page += "             뱅킹 시스템            \n";
        page += "----------------------------------\n";
        page += "  1. 내 정보\n";
        page += "  2. 계좌 관리\n";
        page += "  3. 입금\n";
        page += "  4. 출금\n";
        page += "  5. 송금\n";
        page += "  0. 사용 종료\n";
        page += "----------------------------------\n";
        page += " - 작업 입력: ";
        System.out.print(page);
        workNum = sc.nextInt();
        if (workNum < 0 || workNum > 8) {
            status.setMessage("[Error] 0. ~ 8.  사이로 입력해주세요");
        } else if (workNum == 1) {
            status.setWorkName("my_page");
        } else if (workNum == 2) {
            status.setWorkName("manage_accounts");
        } else if (workNum == 3) {
            status.setWorkName("deposit");
        } else if (workNum == 4) {
            status.setWorkName("withraw");
        } else if (workNum == 5) {
            status.setWorkName("transfer");
        }
        return status;
    }

    public static Status checkUserPage(Status status){
        int workNum;
        String page;
        boolean inputError = true;
        page = "----------------------------------\n";
        page += "           고객 정보 확인\n";
        page += "----------------------------------\n";
        page += " 회원가입 여부를 선택해주세요.\n";
        page += "  1. 회원 고객\n";
        page += "  2. 비회원 고객\n";
        page += "  0. 사용종료\n";
        page += "----------------------------------\n";
        page += "[input] ";
        System.out.print(page);
        workNum = sc.nextInt();

        if (workNum < 0 || workNum > 2) {
            status.setMessage("[Error] 0. ~ 2.  사이로 입력해주세요");
        } else if (workNum == 1) {
            status.setMessage("[Info] 로그인을 진행합니다.");
            status.setUserId("signed_guest");
        } else if (workNum == 2){
            status.setMessage("[Info] 회원가입을 진행합니다.");
            status.setWorkName("sign_up");
        } else if (workNum == 0){
            status.setWorkFlow("stop");
        }

        return status;
    }


    public static User loginPage(){
        User user = new User();
        String page = "";
        String workStr;
        page += "----------------------------------\n";
        page += "              log-in\n";
        page += "----------------------------------\n";
        page += "- ID: ";
        System.out.print(page);
        user.setuserId(sc.next());
        System.out.print("- PW: ");
        user.setPassword(sc.next());
        System.out.println("----------------------------------");

        return user;
    }


    public static User signUpPage (){
        User user = new User();
        String page;

        page = "----------------------------------\n";
        page += "         신규 가입 정보 입력\n";
        page += "----------------------------------\n";
        System.out.println(page);
        System.out.print("id : ");
        user.setuserId(sc.next());
        System.out.print("pw : ");
        user.setPassword(sc.next());
        System.out.print("name: ");
        user.setName(sc.next());
        System.out.println("----------------------------------");

        return user;
    }

    public static Status myInfoPage(Status status, User user) {
        String page;
        page = "----------------------------------\n";
        page += "              내 정보\n";
        page += "----------------------------------\n";
        page += " ID: " + user.getuserId() + "\n";
        page += " 이름: " + user.getName();
        System.out.print(page);
        status.setWorkFlow("goBack");

        return status;
    }

    public static Status manageAccountPage(Status status, ArrayList<Account> accountList) {
        String page;
        int workNum;

        page = "----------------------------------\n";
        page += "      " + status.getUserId() + "님의 계좌 내역\n";
        page += "----------------------------------\n";
        if (accountList.isEmpty()) {
            page += " 보유한 계좌가 없습니다.\n";
        } else {
            page += "No | 상품 |   계좌번호   | 잔액     \n";
            page = "----------------------------------\n";

            Account myAccount = new Account();
            for (int i = 0; i < accountList.size(); i++) {
                myAccount = accountList.get(i);
                page += (i+1) + " | "
                    + myAccount.getProductType() + " | "
                    + myAccount.getaccountNum() + " | "
                    + myAccount.getBalance() + "\n";
            }
        }
        page += "----------------------------------\n";
        page += " 수행할 작업을 입력하세요.\n";
        page += "  1. 계좌 개설\n";
        if (!accountList.isEmpty()) {
            page += "  2. 거래내역 조회\n";
            page += "  3. 계좌 해지\n";
        }
        page += "  0. 뒤로가기\n";
        page += "----------------------------------\n";
        page += "[input] ";
        System.out.println(page);
        workNum = sc.nextInt();
        if (workNum == 1) {
            status.setWorkName("open_account");
            status.setWorkFlow("redirect");
        } else if (workNum == 2) {
            System.out.println("----------------------------------");
            System.out.println(" 조회할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkName("accountHistory");
            status.setWorkFlow("redirect");
        } else if (workNum == 3) {
            System.out.println("----------------------------------");
            System.out.println(" 해지할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkName("close_account");
            status.setWorkFlow("redirect");
        }
        System.out.println("----------------------------------");

        return status;
    }

    public static Trade inputTradeInfo(Status status) {
        Trade trade = new Trade();
        switch (status.getWorkName()) {
            case "deposit" -> trade.setAction("입금");
            case "withraw" -> trade.setAction("출금");
            case "transfer" -> trade.setAction("송금");
        }
        System.out.println("----------------------------------");
        System.out.println("          "+ trade.getAction() +" 정보 입력");
        System.out.println("----------------------------------");
        trade.setUserId(status.getUserId());
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


    public static void printMessage (Status status) {
        System.out.println(status.getMessage());
        status.setMessage("");
    }


    public static void underPage (Status status) {
        String page;

        if (status.getWorkFlow() == "stop") {
            page = "----------------------------------\n";
            page += "       뱅킹시스템 사용 종료\n";
            page += "----------------------------------\n";
            System.out.println(page);
        }

        if (status.getWorkFlow() == "continue"){
            page = "----------------------------------\n";
            page += "[Info] 아무 키나 입력하여 계속: ";
            System.out.print(page);
            String a = sc.next();
        }
    }

}
