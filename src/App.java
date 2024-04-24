//package src;
//
//import src.service.AccountService;
//import src.service.UserService;
//
//import java.sql.*;
//import java.util.List;
//import java.util.Scanner;
//
//public class App {
//
//    public static void main(String[] args) throws SQLException {
//
//        Scanner scanner = new Scanner(System.in);
//        Screen screen = new Screen();
//        String userId = "";
//        List<String> accounts ;
//        System.out.println("뱅킹 시스템을 시작합니다.");
//        while (true) {
//
//            screen.menuId();
//            int num = scanner.nextInt();
//            if(num == 1){ // 1. 회원 가입
//                UserService.createUser(scanner);
//                continue;
//            }else if(num == 2){ // 2. 로그인
//                userId = Service.login(scanner);
//            }else if(num == 0){
//                System.out.println("프로그램을 종료합니다.");
//                return;
//            }else{
//                System.out.println("잘못 입력하셨습니다.");
//            }
//
//            String choice;
//
//            while(true){ // 로그인 시
//                screen.menu();
//                choice = scanner.next();
//                switch (choice) {
//
//                    case "1": // 계좌 관련 기능
//                        screen.login();
//                        int login_num = scanner.nextInt();
//
//                        if(login_num == 1){
//                            AccountService.createAccount(userId);
//                            continue;
//
//                        } else if(login_num == 2){
//                            accounts = AccountService.findAll(userId);
//                            if(!accounts.isEmpty()){
//                                Service.selectAccount(scanner, accounts);
//                                continue;
//                            }else{
//                                System.out.println("계좌가 없습니다.");
//                                continue;
//                            }
//
//                        }else if(login_num == 3){
//                            continue;
//                        }else{
//                            System.out.println("잘못 입력하셨습니다.");
//                            continue;
//                        }
//
//                    case "2": // 입출금 기능
//                        screen.transaction();
//                        int transaction_num = scanner.nextInt();
//                        if(transaction_num == 1){ // 입금
//                            AccountService.deposit(scanner);
//                            System.out.println("입금되었습니다.");
//                            continue;
//
//                        }else if(transaction_num == 2){ // 출금
//                            System.out.println("출금하실 계좌를 선택해주세요.");
//                            accounts = AccountService.findAll(userId);
//                            if(!accounts.isEmpty()){
//                                Service.withDrawAccount(scanner, accounts);
//                                continue;
//
//                            }else{
//                                System.out.println("계좌가 없습니다.");
//                                continue;
//                            }
//
//
//                        }else if(transaction_num == 3){ // 송금
//                            System.out.println("출금하실 계좌를 선택해주세요.");
//                            accounts = AccountService.findAll(userId);
//                            if(!accounts.isEmpty()){
//                                Service.transAccount(scanner, accounts);
//                                continue;
//                            }else{
//                                System.out.println("계좌가 없습니다.");
//                                continue;
//                            }
//
//
//                        }else if(transaction_num == 4){ // 홈으로
//                            continue;
//                        }else{
//                            System.out.println("잘못 입력하셨습니다.");
//                            continue;
//                        }
//
//                    case "3":
//                        System.out.println("로그아웃합니다.");
//                        break;
//
//                    case "0":
//                        System.out.println("프로그램을 종료합니다.");
//                        return;
//
//                    default:
//                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
//                }
//                break;
//            }
//        }
//    }
//}
package src;

import src.service.AccountService;
import src.service.HistoryService;
import src.service.UserService;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Screen screen = new Screen();
    static String userId = "";
    static List<String> accounts;
    public static void main(String[] args) throws SQLException {
        System.out.println("뱅킹 시스템을 시작합니다.");
        bankService();
    }

    public static void bankService() throws SQLException { //앱 실행 함수
        screen.menuId();
        int num = scanner.nextInt();
        if (num == 1) { // 1. 회원 가입
            UserService.createUser(scanner);
            bankService();
        } else if (num == 2) { // 2. 로그인
            login();
            bankFunction(userId);//기능 선택 함수

        } else if (num == 0) {
            System.out.println("프로그램을 종료합니다.");
            return;
        } else {
            System.out.println("잘못 입력하셨습니다.");
            //-------------------------------//
            //여기서 재실행?????
        }
    }

    public static void login() throws SQLException{//로그인 함수
        do { // ID 확인
            System.out.println("사용자 ID를 입력하세요: ");
            userId = scanner.next();
        } while (!UserService.findUser(userId));

        while (true) { // Pw 확인
            if (UserService.findUserPw(userId, scanner))
                break;
        }
    }

    public static void bankFunction(String userId) throws SQLException { //은행 기능 함수
        screen.menu();
        String choice= scanner.next();

        switch (choice) {

            case "1": // 계좌 관련 기능
                accountService();
                break;
            case "2": // 입출금 기능
                moneyService();
                break;
            case "3":
                System.out.println("로그아웃합니다.");
                bankService();
                break;

            case "0":
                System.out.println("프로그램을 종료합니다.");
                return;

            default:
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                bankFunction(userId);
        }
    }

    public static void accountService() throws SQLException {//계좌 생성 및 조회 기능
        screen.login();
        int login_num = scanner.nextInt();
        if (login_num == 1) {//계좌 생성
            AccountService.createAccount(userId);
            bankFunction(userId);

        } else if (login_num == 2) {//계좌 조회
            accounts = AccountService.findAll(userId);
            useAccount();

        } else if (login_num == 3) {
            bankFunction(userId);
        } else {
            System.out.println("잘못 입력하셨습니다.");
            bankFunction(userId);
        }
    }

    public static int choiceAccount(){//사용할 계좌번호 입력
        int account_num;
        if (scanner.hasNextInt()) {
            account_num = scanner.nextInt();
            if (accounts.size() < account_num || account_num < 0) {
                System.out.println("잘못 입력하셨습니다.");
                choiceAccount();
            }
            return account_num;
        }
        System.out.println("정수를 입력해주세요.");
        scanner.next();
        return choiceAccount();
    }

    public static void useAccount() throws SQLException {//계좌 조회 선택
        if (!accounts.isEmpty()) {
            System.out.println("사용하실 계좌를 선택해주세요.");
            int account_num=choiceAccount();
            String selectAccount = accounts.get(account_num - 1);
            System.out.println("1. 계좌삭제");
            System.out.println("2. 계좌이력조회");
            account_num = scanner.nextInt();
            useAccountFunction(account_num, selectAccount);
            bankFunction(userId);
        } else {
            System.out.println("계좌가 없습니다.");
            bankFunction(userId);
        }
    }

    public static void useAccountFunction(int account_num,String selectAccount)  throws SQLException {//계좌 삭제 및 조회 처리
        if (account_num == 1) {
            AccountService.deleteAccount(selectAccount);
        } else if (account_num == 2) {
            HistoryService.getTransactionHistory(selectAccount);
        } else {
            System.out.println("잘못입력하셨습니다. ");
        }
    }

    public static void moneyService() throws SQLException {//입출금, 송금 함수
        screen.transaction();
        int transaction_num = scanner.nextInt();
        if (transaction_num == 1) { // 입금
            AccountService.deposit(scanner);
            bankFunction(userId);

        } else if (transaction_num == 2) { // 출금
            System.out.println("출금하실 계좌를 선택해주세요.");
            accounts = AccountService.findAll(userId);
            withdrawal();

        } else if (transaction_num == 3) { // 송금
            System.out.println("출금하실 계좌를 선택해주세요.");
            accounts = AccountService.findAll(userId);
            remittance();

        } else if (transaction_num == 4) { // 홈으로
            bankFunction(userId);
        } else {
            System.out.println("잘못 입력하셨습니다.");
            bankFunction(userId);
        }
    }

    public static void withdrawal() throws SQLException{ //출금 함수
        if (!accounts.isEmpty()) {
            int account_num=choiceAccount();
            String selectAccount = accounts.get(account_num - 1);
            withdrawalSuccessOrFail(selectAccount);
            bankFunction(userId);
        } else {
            System.out.println("계좌가 없습니다.");
            bankFunction(userId);
        }
    }

    public static void withdrawalSuccessOrFail(String selectAccount) throws SQLException{ //출금 성공 여부 판단 함수
        if (AccountService.withdraw(scanner, selectAccount)) {
            System.out.println("출금되었습니다.");
        }
    }

    public static void remittance() throws SQLException{ // 송금 함수
        if (!accounts.isEmpty()) {
            System.out.println("사용하실 계좌를 선택해주세요.");
            int account_num=choiceAccount();
            String selectAccount = accounts.get(account_num - 1);

            if (AccountService.transfer(scanner, selectAccount)) {
                System.out.println("송금되었습니다.");
            }
            bankFunction(userId);
        } else {
            System.out.println("계좌가 없습니다.");
            bankFunction(userId);
        }
    }
}