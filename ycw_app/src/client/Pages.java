package client;

import account.Account;
import trade.Trade;
import user.User;

import java.util.ArrayList;
import java.util.Scanner;

import controller.*;

public class Pages {

    static Scanner sc = new Scanner(System.in);

    public static Status mainPage(Status status) {
        status.setWorkFlow(Flow.RUN);
        String page, workNum;

        page = "----------------------------------\n";
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
        workNum = sc.next();
        if (workNum.equals("1")) {
            status.setWorkName(Tag.MY_PAGE);
        } else if (workNum.equals("2")) {
            status.setWorkName(Tag.MANAGE_ACCOUNTS);
        } else if (workNum.equals("3")) {
            status.setWorkName(Tag.DEPOSIT);
        } else if (workNum.equals("4")) {
            status.setWorkName(Tag.WITHDRAW);
        } else if (workNum.equals("5")) {
            status.setWorkName(Tag.TRANSFER);
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
        }
        return status;
    }

    public static Status checkUserPage(Status status) {
        String workNum, page;

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
        workNum = sc.next();

        if (workNum.equals("0")) {
            status.setWorkFlow(Flow.STOP);
        } else if (workNum.equals("1")) {
            status.setMessage(Message.INFO_EXCUTE_LOGIN.getMessage());
            status.setUserId(Flow.OLD_GUEST.getFlow());
        } else if (workNum.equals("2")){
            status.setMessage(Message.INFO_EXCUTE_SIGNUP.getMessage());
            status.setWorkName(Tag.SIGN_UP);
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
        }

        return status;
    }


    public static User loginPage() {
        User user = new User();
        String page, workNum;

        page = "----------------------------------\n";
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


    public static User signUpPage() {
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
        status.setWorkName(Tag.MAIN);
        status.setWorkFlow(Flow.CONTINUE);

        return status;
    }

    public static Status manageAccountPage(Status status, ArrayList<Account> accountList) {
        String page, workNum;

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
        workNum = sc.next();
        if (workNum.equals("0")) {
            status.setWorkName(Tag.MAIN);
        }
        else if (workNum.equals("1")) {
            status.setWorkName(Tag.OPEN_ACCOUNT);
        } else if (workNum.equals("2")) {
            System.out.println("----------------------------------");
            System.out.println(" 조회할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkName(Tag.ACCOUNT_HISTORY);
        } else if (workNum.equals("3")) {
            System.out.println("----------------------------------");
            System.out.println(" 해지할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkName(Tag.CLOSE_ACCOUNT);
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
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
        status.setWorkName(Tag.MANAGE_ACCOUNTS);
        status.setWorkFlow(Flow.CONTINUE);

        return status;
    }


    public static Status closeAccountPage(Status status) {
        String page, workNum;

        page = "----------------------------------\n";
        page += " 정말 " + status.getData() + "계좌 삭제를 진행합니까?\n";
        page += "----------------------------------\n";
        page += "  1. 예\n";
        page += "  0. 취소\n";
        page += "----------------------------------\n";
        System.out.println(page);
        workNum = sc.next();
        if (workNum.equals("0")) {
            status.setMessage(Message.INFO_CANCLE_CLOSE_ACCOUNT.getMessage());
            status.setWorkName(Tag.MANAGE_ACCOUNTS);
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
        }

        return status;
    }


    public static Status openAccountPage(Status status) {
        String page, workNum;

        page = "----------------------------------\n";
        page += "           신규 계좌 개설\n";
        page += "----------------------------------\n";
        page += "가입할 상품의 종류를 입력하세요.\n";
        page += "  1. 예금\n";
        page += "  2. 적금\n";
        page += "  3. CMA\n";
        page += "  0. 취소\n";
        page += "----------------------------------\n";
        System.out.println(page);

        workNum = sc.next();
        if (workNum.equals("0")) {
            status.setMessage(Message.INFO_CANCLE_OPEN_ACCOUNT.getMessage());
            status.setWorkName(Tag.MANAGE_ACCOUNTS);
        } else if (workNum.equals("1")) {
            status.setData(Tag.DEPOSIT_ACCOUNT.getTag());
        } else if (workNum.equals("2")) {
            status.setData(Tag.SAVINGS_ACCOUNT.getTag());
        } else if (workNum.equals("3")) {
            status.setData(Tag.CACHE_MANAGEMENT_ACCOUNT.getTag());
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
        }
        System.out.println(Message.INFO_EXCUTE_OPEN_ACCOUNT.getMessage(status.getData()));

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

        if (workNum.equals("1")) {
            status.setData(Tag.MY_ACCOUNT.getTag());
        } else if (workNum.equals("2")) {
            status.setData(Tag.OTHER_ACCOUNT.getTag());
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
        }

        return status;
    }


    public static Status selectAccountPage(Status status, ArrayList<Account> accountList) {
        String page, workNum, work;
        work = "";

        if (status.getWorkName().equals(Tag.DEPOSIT)) {
            work = ;
        } else if (status.getWorkName().equals(Tag.WITHDRAW)) {
            work = "출금";
        } else if (status.getWorkName().equals(Tag.TRANSFER)) {
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
            page += "     " + work + "할 계좌의 번호(No)를 선택하세요.\n";
            page += "  0. 뒤로가기\n";
            page += "----------------------------------\n";
            page += "[input] ";
            System.out.print(page);
            workNum = sc.next();

            if (workNum.equals("0")) {
                status.setWorkName(Tag.MAIN);
                status.setMessage("[Info] " + work + "을 취소합니다.");
                return status;
            } else if (isNumberOnly(workNum)) {
                status.setData(accountList.get(Integer.parseInt(workNum)-1).getaccountNum());
            }
        }
        if (accountList.isEmpty()) {
            page += " 계좌를 개설하시겠습니까?.\n";
            page += "  1. 예\n";
            page += "  2. 아니오\n";
            page += "----------------------------------\n";
            page += "[input] ";
            System.out.print(page);
            workNum = sc.next();
            if (workNum.equals("1")) {
                status.setWorkName(Tag.OPEN_ACCOUNT);
            } else if (workNum.equals("2")) {
                status.setMessage("[Info] 작업을 취소합니다.");
                status.setWorkName(Tag.MAIN);
            } else {
                status.setMessage("[Error] 1. ~ 2. 사이로 입력해주세요");
            }
        }
        System.out.println("----------------------------------");

        return status;
    }

    public static Trade inputTradeInfo(Status status) {
        Trade trade = new Trade();

        switch (status.getWorkName()) {
            case DEPOSIT -> trade.setAction("입금");
            case WITHDRAW -> trade.setAction("출금");
            case TRANSFER -> trade.setAction("송금");
        }

        System.out.println("----------------------------------");
        System.out.println("          "+ trade.getAction() +" 정보 입력");
        System.out.println("----------------------------------");

        switch (trade.getAction()) {
            case "입금", "출금" -> {
                trade.setRequestAccount("-");
                if (trade.getAction().equals("입금") && status.getData().equals(Tag.OTHER_ACCOUNT.getTag())){
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

        if (status.getWorkFlow().equals(Flow.STOP)) {
            page = "----------------------------------\n";
            page += "       뱅킹시스템 사용 종료\n";
            page += "----------------------------------\n";
            System.out.println(page);
        }

        if (status.getWorkFlow().equals(Flow.CONTINUE)){
            page = "[Info] 아무 키나 입력하여 계속: ";
            System.out.print(page);
            String a = sc.next();
        }
    }

    public static boolean isNumberOnly(String workNum) {
        final String REGEX = "[0-9]+";
        return workNum.matches(REGEX);
    }

}
