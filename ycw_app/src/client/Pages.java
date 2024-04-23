package client;

import account.Account;
import trade.Trade;
import user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Pages {

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
        page += " [input] ";
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
            status.setWorkName("withdraw");
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
        System.out.println(page);
        status.setWorkName("main");
        status.setWorkFlow("continue");

        return status;
    }

    public static Status manageAccountPage(Status status, ArrayList<Account> accountList) {
        String page;
        int workNum;

        page = "----------------------------------\n";
        page += "        나의 계좌 내역 (" + accountList.size() +")\n";
        page += "----------------------------------\n";
        if (accountList.isEmpty()) {
            page += " 보유한 계좌가 없습니다.\n";
        } else {
            page += "No | 상품 |   계좌번호   | 잔액     \n";
            page += "----------------------------------\n";

            Account myAccount = new Account();
            for (int i = 0; i < accountList.size(); i++) {
                myAccount = accountList.get(i);
                if ( i < 9 ) {
                    page += " " + (i+1) + " | ";
                } else {
                    page += (i+1) + " | ";
                }
                page += myAccount.getProductType() + " | ";
                page += myAccount.getaccountNum() + " | ";
                page += myAccount.getBalance() + "\n";
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
        System.out.print(page);
        workNum = sc.nextInt();
        if (workNum == 0) {
            status.setWorkName("main");
            status.setWorkFlow("run");
        }
        else if (workNum == 1) {
            status.setWorkName("open_account");
            status.setWorkFlow("redirect");
        } else if (workNum == 2) {
            System.out.println("----------------------------------");
            System.out.println(" 조회할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkName("account_history");
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

    public static Status accountHistoryPage(Status status, ArrayList<Trade> tradeList) {
        String page;
        page = "----------------------------------\n";
        page += "     "+ status.getData() + " 계좌 거래내역\n";
        page += "----------------------------------\n";
        if (tradeList.isEmpty()) {
            page += "거래내역이 없습니다.\n";
        } else {
            page += "No | 거래 | 거래 계좌번호 | 거래액     \n";
            page += "----------------------------------\n";
            // 거래 내역 출력
            String accountNum;
            for (Trade trade : tradeList) {
                if (trade.getRequestAccount().equals("-")) {
                    accountNum = " 본인 거래 ";
                } else {
                    accountNum = trade.getTargetAccount();
                }
                if ( trade.getTradeId() < 10 ) {
                    page += " " + trade.getTradeId() + " | ";
                } else {
                    page += trade.getTradeId() + " | ";
                }
                page += trade.getAction() + " | ";
                page += accountNum + " | ";
                page += trade.getAmount() + "\n";
            }
        }
        page += "----------------------------------\n";
        System.out.println(page);
        status.setWorkName("manage_accounts");
        status.setWorkFlow("continue");

        return status;
    }


    public static Status closeAccountPage(Status status) {
        String page;

        page = "----------------------------------\n";
        page += " 정말 " + status.getData() + "계좌 삭제를 진행합니까?\n";
        page += "----------------------------------\n";
        page += "  1. 예\n";
        page += "  2. 아니오\n";
        page += "----------------------------------\n";
        System.out.println(page);
        int workNum = sc.nextInt();
        if (workNum < 1 || workNum > 2) {
            status.setMessage("[Error] 1. ~ 2. 사이로 입력해주세요");
        } else if (workNum == 2) {
            status.setMessage("[Info] 계좌 해지를 취소합니다.");
            status.setWorkName("manage_accounts");
            status.setWorkFlow("redirect");
        }

        return status;
    }


    public static Status openAccountPage(Status status) {
        String page = "";

        page += "----------------------------------\n";
        page += "           신규 계좌 개설\n";
        page += "----------------------------------\n";
        page += "가입할 상품의 종류를 입력하세요.\n";
        page += "  1. 예금\n";
        page += "  2. 적금\n";
        page += "  3. CMA\n";
        page += "  0. 취소\n";
        page += "----------------------------------\n";
        System.out.println(page);

        int workNum = sc.nextInt();
        if (workNum < 0 || workNum > 3) {
            status.setMessage("[Error] 0. ~ 3. 사이로 입력해주세요");
        } else if (workNum == 0) {
            status.setMessage("[Info] 상품 가입을 취소합니다.");
            status.setWorkName("manage_accounts");
            status.setWorkFlow("redirect");
        } else if (workNum == 1) {
            status.setData("예금");
        } else if (workNum == 2) {
            status.setData("적금");
        } else if (workNum == 3) {
            status.setData("CMA");
        }
        System.out.println("[Info] " + status.getData()  + " 상품 가입을 진행합니다." );

        return status;
    }

    public static Status checkDepoistToOtherPage(Status status) {
        String page;
        page = "----------------------------------\n";
        page += "           입금 대상 선택\n";
        page += "----------------------------------\n";
        page += "  1. 본인 계좌\n";
        page += "  2. 타인 계좌\n";
        page += "----------------------------------\n";
        page += "[input] ";
        System.out.print(page);
        String workNum = sc.next();

        if (!workNum.equals("1") && !workNum.equals("2")){
            status.setMessage("[Error] 1. ~ 2. 사이로 입력해주세요");
        } else if (workNum.equals("1")) {
            status.setData("my_account");
        } else if (workNum.equals("2")) {
            status.setData("others_account");
        }

        return status;
    }


    public static Status selectAccountPage(Status status, ArrayList<Account> accountList) {
        String page;
        String work = "";
        String workNum;

        if (status.getWorkName().equals("deposit")) {
            work = "입금";
        } else if (status.getWorkName().equals("withdraw")) {
            work = "출금";
        } else if (status.getWorkName().equals("transfer")) {
            work = "송금";
        }

        page = "----------------------------------\n";
        page += "        " + work + "할 내 계좌 선택 \n";
        page += "----------------------------------\n";
        if (accountList.isEmpty()) {
            page += " 보유한 계좌가 없습니다.\n";
        } else {
            page += "No | 상품 |   계좌번호   | 잔액     \n";
            page += "----------------------------------\n";

            Account myAccount = new Account();
            for (int i = 0; i < accountList.size(); i++) {
                myAccount = accountList.get(i);
                if ( i < 9 ) {
                    page += " " + (i+1) + " | ";
                } else {
                    page += (i+1) + " | ";
                }
                page += myAccount.getProductType() + " | ";
                page += myAccount.getaccountNum() + " | ";
                page += myAccount.getBalance() + "\n";
            }
        }
        page += "----------------------------------\n";

        if (!accountList.isEmpty()) {
            page += "[Info] " + work + "할 계좌의 번호(No)를 선택하세요.\n";
            page += "  0. 뒤로가기\n";
            page += "----------------------------------\n";
            page += "[input] ";
            System.out.print(page);
            workNum = sc.next();
            System.out.println(workNum);
            System.out.println(Integer.parseInt(workNum));
            if (workNum.equals("0")) {
                status.setWorkName("main");
                status.setWorkFlow("redirect");
                status.setMessage("[Info] " +  work + "을 취소합니다." );
            } else {
                status.setData(accountList.get(Integer.parseInt(workNum)-1).getaccountNum());
            }
            System.out.println(status.toString());
        }
        if (accountList.isEmpty()) {
            page += " 계좌를 개설하시겠습니까?.\n";
            page += "  1. 예\n";
            page += "  2. 아니오\n";
            page += "----------------------------------\n";
            page += "[input] ";
            System.out.print(page);
            workNum = sc.next();
            if (!workNum.equals("1") && !workNum.equals("2")) {
                status.setMessage("[Error] 1. ~ 2. 사이로 입력해주세요");
            } else if (workNum.equals("1")) {
                status.setWorkName("open_account");
                status.setWorkFlow("redirect");
            } else if (workNum.equals("2")) {
                status.setMessage("[Info] 작업을 취소합니다.");
                status.setWorkName("main");
                status.setWorkFlow("redirect");
            }
        }
        System.out.println("----------------------------------");

        return status;
    }

    public static Trade inputTradeInfo(Status status) {
        Trade trade = new Trade();
        switch (status.getWorkName()) {
            case "deposit" -> trade.setAction("입금");
            case "withdraw" -> trade.setAction("출금");
            case "transfer" -> trade.setAction("송금");
        }
        System.out.println("----------------------------------");
        System.out.println("          "+ trade.getAction() +" 정보 입력");
        System.out.println("----------------------------------");

        switch (trade.getAction()) {
            case "입금", "출금" -> {
                trade.setRequestAccount("-");
                if (trade.getAction().equals("입금") && status.getData().equals("others_account")){
                    System.out.print("입금 계좌: ");
                    trade.setTargetAccount(sc.next());
                } else {
                    trade.setTargetAccount(status.getData());
                }
            }
            case "송금" -> {
                trade.setRequestAccount(status.getData());
                System.out.print("받는 계좌: ");
                trade.setTargetAccount(sc.next());
            }
        }
        System.out.print(trade.getAction() + " 금액: ");
        trade.setAmount(sc.nextInt());
        System.out.println("----------------------------------");

        return trade;
    }


    public static void printMessage (Status status) {
        if (!status.getMessage().equals("")) {
            System.out.println(status.getMessage());
            status.setMessage("");
        }
    }


    public static void underPage (Status status) {
        String page;

        if (status.getWorkFlow().equals("stop")) {
            page = "----------------------------------\n";
            page += "       뱅킹시스템 사용 종료\n";
            page += "----------------------------------\n";
            System.out.println(page);
        }

        if (status.getWorkFlow().equals("continue")){
            page = "[Info] 아무 키나 입력하여 계속: ";
            System.out.print(page);
            String a = sc.next();
        }
    }

}
