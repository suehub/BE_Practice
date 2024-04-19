package src;

import src.service.AccountService;
import src.service.HistoryService;
import src.service.UserService;

import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--------------------------");
            System.out.println("원하는 작업을 선택하세요:");
            System.out.print("1. 사용자 생성\t");
            System.out.print("2. 계좌 생성\t");
            System.out.println("3. 계좌 삭제");
            System.out.print("4. 입금\t\t\t");
            System.out.print("5. 출금\t\t\t");
            System.out.println("6. 송금");
            System.out.print("7. 거래 내역 조회\t");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 입력 버퍼 비우기

            switch (choice) {
                case 1:
                    UserService.createUser(scanner);
                    break;
                case 2:
                    AccountService.createAccount(scanner);
                    break;
                case 3:
                    AccountService.deleteAccount(scanner);
                    break;
                case 4:
                    AccountService.deposit(scanner);
                    break;
                case 5:
                    AccountService.withdraw(scanner);
                    break;
                case 6:
                    AccountService.transfer(scanner);
                    break;
                case 7:
                    HistoryService.getTransactionHistory(scanner);
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }
}

