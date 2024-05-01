package main;

import accountManager.AccountManager;
import member.Member;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
                    System.out.println("프로그램 종료");
                    System.exit(0);
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
            }


            while(isLogin) {
                printMenu();
                int menu2 = getMenuChoice(sc);
                switch(menu2) {
                    case 1:
                        Map<String, Double> accountLists = accountManager.checkAccountDetails(member.getId());
                        System.out.println(member.getName().trim() + "님의 계좌목록입니다.");
                        System.out.println("계좌번호\t\t\t\t\t\t잔액");
                        System.out.println("------------------------------------------------------");

                        for (Map.Entry<String, Double> entry : accountLists.entrySet()) {
                            String accountNumber = entry.getKey();
                            double balance = entry.getValue();
                            System.out.printf("%s\t\t\t\t%,12.0f원\n", accountNumber, balance); // 오른쪽 정렬 및 금액 자릿수에 맞춰 출력
                        }
                        break;
                    case 2:
                        System.out.print("입금할 계좌 번호를 입력하세요: ");
                        String accountNumber = sc.next();
                        System.out.print("입금할 금액을 입력하세요: ");
                        double depositAmount = sc.nextDouble();
                        accountManager.deposit(member.getId(), accountNumber, depositAmount);
                        break;
                    case 3:
                        System.out.print("출금할 계좌 번호를 입력하세요: ");
                        String withdrawalAccountNumber = sc.next();
                        System.out.print("출금할 금액을 입력하세요: ");
                        double withdrawalAmount = sc.nextDouble();
                        accountManager.withdrawal(member.getId(), withdrawalAccountNumber, withdrawalAmount);
                        break;
                    case 4:
                        System.out.print("계좌 번호를 입력하세요: ");
                        withdrawalAccountNumber = sc.next();
                        System.out.print("송금할 계좌번호를 입력하세요: ");
                        String depositAccountNumber = sc.next();
                        System.out.print("금액을 입력하세요: ");
                        double transferAmount = sc.nextDouble();
                        accountManager.transfer(withdrawalAccountNumber, depositAccountNumber, transferAmount);
                        break;
                    case 5:
                        System.out.print("이름을 입력하세요: ");
                        String userName = sc.next();
                        accountManager.createAccount(userName);
                        break;
                    case 6:
                        System.out.print("계좌 번호를 입력하세요: ");
                        String deleteAccountNumber = sc.next();
                        accountManager.deleteAccount(deleteAccountNumber);
                        break;
                    case 7 :
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
        System.out.println("=====================================================");
        System.out.println("1.로그인 2.회원가입 3.종료");
        System.out.println("=====================================================");
    }

    public static void printMenu() {
        System.out.println("=======================================================");
        System.out.println("1.계좌 목록 2.입금 3.출금 4.송금 5.계좌 생성 6.계좌 삭제 7.로그아웃");
        System.out.println("=======================================================");
    }

    public static int getMenuChoice(Scanner sc) {
        System.out.print("메뉴 선택: ");
        return sc.nextInt();
    }
}
