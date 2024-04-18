package main;

import accountManager.AccountManager;

import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();

        while(true) {
            printMenu();
            int menu = getMenuChoice(sc);
            switch (menu) {
                case 1:
                    System.out.print("계좌번호를 입력하세요: ");
                    String accountNumber = sc.next();
                    accountManager.checkAccountDetails(accountNumber);
                    break;
                case 2:
                    System.out.print("계좌 번호를 입력하세요: ");
                    String depositAccountNumber = sc.next();
                    System.out.print("입금할 금액을 입력하세요: ");
                    double depositAmount = sc.nextDouble();
                    accountManager.deposit(depositAccountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("계좌 번호를 입력하세요: ");
                    String withdrawAccountNumber = sc.next();
                    System.out.print("출금할 금액을 입력하세요: ");
                    double withdrawAmount = sc.nextDouble();
                    accountManager.withdraw(withdrawAccountNumber, withdrawAmount);
                    break;
                case 4:
                    System.out.println("송금 기능");
                    break;
                case 5:
                    System.out.print("이름을 입력해주세요: ");
                    String userName = sc.next();
                    accountManager.createAccount(userName);
                    break;
                case 6:
                    System.out.print("계좌 번호를 입력하세요: ");
                    String deleteAccountNumber = sc.next();
                    accountManager.deleteAccount(deleteAccountNumber);
                    break;
                case 7:
                    System.out.println("프로그램 종료");
                    System.exit(0);
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 선택해주세요.");
            }
        }

    }

    public static void printMenu() {
        System.out.println("=====================================================");
        System.out.println("1.계좌 조회 2.입금 3.출금 4.송금 5.계좌 생성 6.계좌 삭제 7. 종료");
        System.out.println("=====================================================");
    }

    public static int getMenuChoice(Scanner sc) {
        System.out.print("메뉴 선택: ");
        return sc.nextInt();
    }
}
