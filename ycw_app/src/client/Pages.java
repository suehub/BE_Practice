package client;

import account.Account;
import trade.Trade;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
            status.setWorkTag(Tag.MY_PAGE);
        } else if (workNum.equals("2")) {
            status.setWorkTag(Tag.MANAGE_ACCOUNTS);
        } else if (workNum.equals("3")) {
            status.setWorkTag(Tag.DEPOSIT);
        } else if (workNum.equals("4")) {
            status.setWorkTag(Tag.WITHDRAW);
        } else if (workNum.equals("5")) {
            status.setWorkTag(Tag.TRANSFER);
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
            status.setWorkTag(Tag.SIGN_UP);
        } else {
            status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
        }

        return status;
    }


    public static User loginPage() {
        User user = new User();
        String page;

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
        status.setWorkTag(Tag.MAIN);
        status.setWorkFlow(Flow.CONTINUE);

        return status;
    }

    public static Status manageAccountPage(Status status, ArrayList<Account> accountList) {
        String page, workNum, account_owner;

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
        if (accountList.isEmpty()){
            page += "  1. 계좌 개설\n";
        } else if (!accountList.isEmpty()) {
            page += "  1. 입금\n";
            page += "  2. 출금\n";
            page += "  3. 송금\n";
            page += "  4. 거래내역 조회\n";
            page += "  5. 계좌 개설\n";
            page += "  6. 계좌 해지\n";
        } else {
            page += "[Error] 오류가 발생했습니다!";
        }
        page += "  0. 뒤로가기\n";
        page += "----------------------------------\n";
        page += "[input] ";
        System.out.print(page);
        workNum = sc.next();
        if (workNum.equals("0")) {
            status.setWorkTag(Tag.MAIN);
        } else if (accountList.isEmpty() && workNum.equals("1")
                   || !accountList.isEmpty() && workNum.equals("5")) {
            status.setWorkTag(Tag.OPEN_ACCOUNT);
        } else if (!accountList.isEmpty() && (workNum.equals("1") || workNum.equals("2") || workNum.equals("3"))) {
            String action = "";
            switch (workNum){
                case "1" -> { status.setWorkTag(Tag.DEPOSIT);
                              status.setDataValue(Tag.PUT_DATA,Tag.A, Tag.MY_ACCOUNT.getTag());
                              action = Tag.ACTION_DEPOSIT.getTag();
                            }
                case "2" -> { status.setWorkTag(Tag.WITHDRAW);
                              action = Tag.ACTION_WITHDRAW.getTag();

                            }
                case "3" -> { status.setWorkTag(Tag.TRANSFER);
                              action = Tag.ACTION_TRANSFER.getTag();
                            }
            }
            page += "     " + action + "할 계좌의 번호(No)를 선택하세요.\n";
            page += "  0. 뒤로가기\n";
            page += "----------------------------------\n";
            page += "[input] ";
            System.out.print(page);
            workNum = sc.next();
            if (workNum.equals("0")) {
                status.setWorkTag(Tag.MANAGE_ACCOUNTS);
                status.setMessage(Message.INFO_CANCLE_TRADE.getMessage(action));
                return status;
            } else if (isNumberOnly(workNum)) {
                status.setData(accountList.get(Integer.parseInt(workNum)-1).getaccountNum());
            }
        } else if (!accountList.isEmpty() && workNum.equals("4")) {
            System.out.println("----------------------------------");
            System.out.println(" 조회할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkTag(Tag.ACCOUNT_HISTORY);
        } else if (!accountList.isEmpty() && workNum.equals("6")) {
            System.out.println("----------------------------------");
            System.out.println(" 해지할 계좌의 번호(No)를 선택 하세요.");
            System.out.println("----------------------------------");
            System.out.print("[input] ");
            status.setData(accountList.get(sc.nextInt()-1).getaccountNum());
            status.setWorkTag(Tag.CLOSE_ACCOUNT);
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
        status.setWorkTag(Tag.MANAGE_ACCOUNTS);
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
            status.setWorkTag(Tag.MANAGE_ACCOUNTS);
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
            status.setWorkTag(Tag.MANAGE_ACCOUNTS);
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
        String page, workNum, action;
        action = "";

        if (status.getWorkTag().equals(Tag.DEPOSIT)) {
            action = Tag.ACTION_DEPOSIT.getTag();
        } else if (status.getWorkTag().equals(Tag.WITHDRAW)) {
            action = Tag.ACTION_WITHDRAW.getTag();
        } else if (status.getWorkTag().equals(Tag.TRANSFER)) {
            action = Tag.ACTION_TRANSFER.getTag();
        }

        page = "----------------------------------\n";
        page += "        " + action + "할 내 계좌 선택 \n";
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
            page += "     " + action+ "할 계좌의 번호(No)를 선택하세요.\n";
            page += "  0. 뒤로가기\n";
            page += "----------------------------------\n";
            page += "[input] ";
            System.out.print(page);
            workNum = sc.next();

            if (workNum.equals("0")) {
                status.setWorkTag(Tag.MAIN);
                status.setMessage(Message.INFO_CANCLE_TRADE.getMessage(action));
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
                status.setWorkTag(Tag.OPEN_ACCOUNT);
            } else if (workNum.equals("2")) {
                status.setMessage(Message.INFO_CANCLE_OPEN_ACCOUNT.getMessage());
                status.setWorkTag(Tag.MAIN);
            } else {
                status.setMessage(Message.ERROR_WRONG_INPUT.getMessage());
            }
        }
        System.out.println("----------------------------------");

        return status;
    }

    public static Trade inputTradeInfo(Status status) {
        Trade trade = new Trade();

        switch (status.getWorkTag()) {
            case DEPOSIT -> trade.setAction(Tag.ACTION_DEPOSIT);
            case WITHDRAW -> trade.setAction(Tag.ACTION_WITHDRAW);
            case TRANSFER -> trade.setAction(Tag.ACTION_TRANSFER);
        }

        System.out.println("----------------------------------");
        System.out.println("          "+ trade.getAction() +" 정보 입력");
        System.out.println("----------------------------------");

        switch (trade.getAction()) {
            case ACTION_DEPOSIT, ACTION_WITHDRAW  -> {
                trade.setRequestAccount(Tag.DEFAULT_ACCOUNT.getTag());
                if (trade.getAction() == Tag.ACTION_DEPOSIT && status.getData().equals(Tag.OTHER_ACCOUNT)) {
                    System.out.print("입금 계좌: ");
                    trade.setTargetAccount(sc.next());
                } else {
                    trade.setTargetAccount(status.getData());
                }
            }
            case ACTION_TRANSFER -> {
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
        if (!status.getMessage().equals(Tag.DEFAULT_DATA.getTag())) {
            System.out.println(status.getMessage());
            status.setMessage(Tag.DEFAULT_DATA.getTag());
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
