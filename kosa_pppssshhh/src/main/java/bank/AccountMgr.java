package bank;

import java.util.Scanner;

/**
 * Account Manager 1
 */
public class AccountMgr {
    // 클래스 변수
    static Account[] accounts = new Account[100];
    static int index = 0;
    static Scanner scan = new Scanner(System.in); // Scanner 객체 재사용

    // 잔액을 조회하다.
    public static void retrieveBalance() {
        System.out.print("계좌번호 : ");
        String accountNumber = scan.nextLine().trim(); // trim() 적용

        Account account = findAccountByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("계좌번호를 정확히 입력해주세요.");
            return;
        }
        System.out.printf("잔액 : %d원%n", account.getBalance());
    }

    // 출금하다.
    public static void devit() {
        System.out.print("계좌번호: ");
        String accountNumber = scan.nextLine().trim(); // trim() 적용

        Account account = findAccountByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("계좌번호를 정확히 입력해주세요.");
            return; // 메소드를 빠져나올 때 사용한다.
        }

        System.out.print("금액 : ");
        int amount = Integer.parseInt(scan.nextLine());

        try {
            account.withdraw(amount);
            System.out.println("출금이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 입금하다.
    public static void credit() {
        System.out.print("계좌번호 : ");
        String accountNumber = scan.nextLine().trim(); // trim() 적용

        Account account = findAccountByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("계좌번호를 정확히 입력해주세요.");
            return; // 메소드를 빠져나올 때 사용한다.
        }

        System.out.print("금액 : ");
        int amount = Integer.parseInt(scan.nextLine());

        account.deposit(amount);
        System.out.println("입금이 완료되었습니다.");
    }

    // 전체 계좌를 조회하다.
    public static void retrieveAccount() {
        for (int i = 0; i < index; i++) {
            accounts[i].printAccounts();
        }
    }

    // 계좌번호에 해당하는 고객 계좌를 조회하다.
    public static Account findAccountByAccountNumber(String accountNumber) {
        for (int i = 0; i < index; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    // 계좌를 개설하다.
    public static void createAccount() {
        System.out.print("계좌번호 : ");
        String accountNumber = scan.nextLine().trim(); // trim() 적용

        System.out.print("이름 : ");
        String name = scan.nextLine();

        System.out.print("비밀번호 : ");
        String pwd = scan.nextLine();

        System.out.print("잔고 : ");
        int balance = Integer.parseInt(scan.nextLine());

        Account account = new Account(accountNumber, name, pwd, balance);
        accounts[index++] = account; // 배열에 계좌 추가 후 index 증가
    }

    public static void main(String[] args) {
        boolean isExit = false;

        do {
            System.out.println("===== 은행 입출금 관리 시스템 =====");
            System.out.println("1. 계좌개설");
            System.out.println("2. 입금");
            System.out.println("3. 출금");
            System.out.println("4. 잔액조회");
            System.out.println("5. 전체 계좌 조회");
            System.out.println("6. 종료");
            System.out.println("항목을 입력하세요.");

            int menu = Integer.parseInt(scan.nextLine()); // 개행 문자 처리

            switch (menu) {
                case 1: // 계좌개설
                    createAccount();
                    break;
                case 2: // 입금
                    credit();
                    break;
                case 3: // 출금
                    devit();
                    break;
                case 4: // 잔액조회
                    retrieveBalance();
                    break;
                case 5: // 전체 계좌 조회
                    retrieveAccount();
                    break;
                case 6: // 종료
                    isExit = true;
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
            }

        } while (!isExit);

        // Scanner 종료
        scan.close();
    }
}
