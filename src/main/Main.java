package main;

import accountManager.AccountManager;
import member.Member;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Member member = new Member();
        boolean isLogin = false;

        AccountManager accountManager = new AccountManager();

        while(true) {
            startMenu();
            int menu1 = getMenuChoice(sc);
            switch(menu1) {
                case 1:
                    while(true) {
                        System.out.println("로그인 >>");
                        System.out.print("아이디: ");
                        String id = sc.next();
                        if (id.equals("0")) break;
                        System.out.print("비밀번호: ");
                        String password = sc.next();
                        int log = member.login(id, password);
                        if(log == 1) {
                            System.out.println("로그인 되었습니다.");
                            isLogin = true;
                            break;
                        } else {
                            System.out.println("아이디 또는 비밀번호가 일치하지 않습니다. (로그인 종료: 0)");
                        }
                    }
                    break;
                case 2:
                    System.out.println("회원가입 >>");
                    System.out.print("이름: ");
                    String newName = sc.next();
                    System.out.print("아이디: ");
                    String newId = sc.next();
                    System.out.print("비밀번호: ");
                    String newPassword = sc.next();
                    member.signup(newName, newId, newPassword);

//                    while(true) {

//                        System.out.print("비밀번호 재입력: ");
//                        String newPassword2 = sc.nextLine();
//                        if (newPassword.equals(newPassword2)) {
//                            member.signup(newName, newId, newPassword);
//                            break;
//                        } else {
//                            System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
//                        }
//                    }
                    break;
                case 3:
                    System.out.println("JAVABANK 종료");
                    System.exit(0);
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
            }


            while(isLogin) {
                printMenu();
                int menu2 = getMenuChoice(sc);
                switch(menu2) {
                    case 1:
                        Map<String, Double> accountLists = accountManager.showAccounts(member.getId());
                        if (accountLists.isEmpty()) {
                            System.out.println("계좌 목록이 없습니다.");
                            break;
                        }
                        System.out.println(member.getName().trim() + "님의 계좌목록입니다.");
                        System.out.println("계좌번호\t\t\t\t\t\t잔액");
                        System.out.println("------------------------------------------------------");

                        for (Map.Entry<String, Double> entry : accountLists.entrySet()) {
                            String accountNumber = entry.getKey();
                            double balance = entry.getValue();
                            System.out.printf("%s\t\t\t\t%,12.0f원\n", accountNumber, balance);
                        }
                        break;
                    case 2:
                        System.out.print("조회 계좌 번호: ");
                        String accountNumber = sc.next();
                        accountManager.checkAccountDetails(accountNumber);
                        break;
                    case 3:
                        System.out.print("입금 계좌 번호: ");
                        accountNumber = sc.next();
                        System.out.print("입금 금액: ");
                        double depositAmount = sc.nextDouble();
                        System.out.print("계좌 비밀번호: ");
                        String accountPassword = sc.next();
                        accountManager.deposit(member.getId(), accountNumber, depositAmount, accountPassword);
                        break;
                    case 4:
                        System.out.print("출금 계좌 번호: ");
                        String withdrawalAccountNumber = sc.next();
                        System.out.print("출금 금액: ");
                        double withdrawalAmount = sc.nextDouble();
                        System.out.print("계좌 비밀번호: ");
                        accountPassword = sc.next();
                        accountManager.withdrawal(member.getId(), withdrawalAccountNumber, withdrawalAmount, accountPassword);
                        break;
                    case 5:
                        System.out.print("계좌 번호: ");
                        withdrawalAccountNumber = sc.next();
                        System.out.print("송금할 계좌번호 입력: ");
                        String depositAccountNumber = sc.next();
                        System.out.print("금액 입력: ");
                        double transferAmount = sc.nextDouble();
                        System.out.print("계좌 비밀번호: ");
                        accountPassword = sc.next();
                        accountManager.transfer(withdrawalAccountNumber, depositAccountNumber, transferAmount, accountPassword);
                        break;
                    case 6:
                        System.out.print("생성 계좌 비밀번호 입력: ");
                        accountPassword = sc.next();
                        accountManager.createAccount(member.getId(), accountPassword);
                        break;
                    case 7:
                        System.out.print("삭제 계좌 번호: ");
                        String deleteAccountNumber = sc.next();
                        System.out.print("계좌 비밀번호: ");
                        String deletePassword = sc.next();
                        accountManager.deleteAccount(deleteAccountNumber, deletePassword);
                        break;
                    case 8:
                        member = null;
                        isLogin = false;
                        System.out.println("로그아웃되었습니다.");
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다. 다시 선택해주세요.");
                }

            }

        }

    }

    public static void startMenu() {
        System.out.println("=================================================================");
        System.out.println("1.로그인 2.회원가입 3.종료");
        System.out.println("=================================================================");
    }

    public static void printMenu() {
        System.out.println("=================================================================");
        System.out.println("1.계좌 목록 2.계좌 조회 3.입금 4.출금 5.송금 6.계좌 생성 7.계좌 삭제 8.로그아웃");
        System.out.println("=================================================================");
    }

    public static int getMenuChoice(Scanner sc) {
        System.out.print("메뉴 선택: ");
        return sc.nextInt();
    }
}
