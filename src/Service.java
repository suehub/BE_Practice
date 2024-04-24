package src;

import src.service.AccountService;
import src.service.HistoryService;
import src.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Service {
    public static String login(Scanner scanner) throws SQLException {
        String userId = "";
        while(true){ // ID 확인
            System.out.println("사용자 ID를 입력하세요: ");
            userId = scanner.next();
            if(UserService.findUser(userId)){
                break;
            }
        }

        while(true){ // Pw 확인
            if(UserService.findUserPw(scanner)){
                break;
            }
        }
        return userId;
    }

    public static void selectAccount(Scanner scanner, List<String> accounts) throws SQLException {
        int account_num;
        System.out.println("사용하실 계좌를 선택해주세요.");
        while (true) {
            if (scanner.hasNextInt()) {
                account_num = scanner.nextInt();
                if (accounts.size() < account_num || account_num < 0) {
                    System.out.println("잘못 입력하셨습니다.");
                    continue;
                }
                break;
            } else {
                System.out.println("정수를 입력해주세요.");
                scanner.next();
            }
        }
        String selectAccount = accounts.get(account_num - 1);
        System.out.println("1. 계좌삭제");
        System.out.println("2. 계좌이력조회");
        account_num = scanner.nextInt();
        if(account_num == 1){
            AccountService.deleteAccount(selectAccount);
        }else if(account_num == 2){
            HistoryService.getTransactionHistory(selectAccount);
        }else{
            System.out.println("잘못입력하셨습니다. ");
        }
    }

    public static void withDrawAccount(Scanner scanner, List<String> accounts) throws SQLException {
        int account_num;
        System.out.println("사용하실 계좌를 선택해주세요.");
        while(true){
            if (scanner.hasNextInt()) {
                account_num = scanner.nextInt();
                if(accounts.size() < account_num || account_num < 0) {
                    System.out.println("잘못 입력하셨습니다.");
                    continue;
                }
                break;
            } else {
                System.out.println("정수를 입력해주세요.");
                scanner.next();
            }
        }
        String selectAccount = accounts.get(account_num-1);
        if(AccountService.withdraw(scanner, selectAccount)){
            System.out.println("출금되었습니다.");
        }
    }

    public static void transAccount(Scanner scanner, List<String> accounts) throws SQLException {
        int account_num;
        System.out.println("사용하실 계좌를 선택해주세요.");
        while(true){
            if (scanner.hasNextInt()) {
                account_num = scanner.nextInt();
                if(accounts.size() < account_num || account_num < 0) {
                    System.out.println("잘못 입력하셨습니다.");
                    continue;
                }
                break;
            } else {
                System.out.println("정수를 입력해주세요.");
                scanner.next();
            }
        }
        String selectAccount = accounts.get(account_num-1);

        if(AccountService.transfer(scanner, selectAccount)){
            System.out.println("송금되었습니다.");
        }
    }

}
