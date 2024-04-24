package src;

import src.service.AccountService;
import src.service.UserService;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Screen screen = new Screen();
        String userId = "";
        List<String> accounts ;
        System.out.println("뱅킹 시스템을 시작합니다.");
        while (true) {

            screen.menuId();
            int num = scanner.nextInt();
            if(num == 1){ // 1. 회원 가입
                UserService.createUser(scanner);
                continue;
            }else if(num == 2){ // 2. 로그인
                userId = Service.login(scanner);
            }else if(num == 0){
                System.out.println("프로그램을 종료합니다.");
                return;
            }else{
                System.out.println("잘못 입력하셨습니다.");
            }

            String choice;

            while(true){ // 로그인 시
                screen.menu();
                choice = scanner.next();
                switch (choice) {

                    case "1": // 계좌 관련 기능
                        screen.login();
                        int login_num = scanner.nextInt();

                        if(login_num == 1){
                            AccountService.createAccount(userId);
                            continue;

                        } else if(login_num == 2){
                            accounts = AccountService.findAll(userId);
                            if(!accounts.isEmpty()){
                                Service.selectAccount(scanner, accounts);
                                continue;
                            }else{
                                System.out.println("계좌가 없습니다.");
                                continue;
                            }

                        }else if(login_num == 3){
                            continue;
                        }else{
                            System.out.println("잘못 입력하셨습니다.");
                            continue;
                        }

                    case "2": // 입출금 기능
                        screen.transaction();
                        int transaction_num = scanner.nextInt();
                        if(transaction_num == 1){ // 입금
                            AccountService.deposit(scanner);
                            System.out.println("입금되었습니다.");
                            continue;

                        }else if(transaction_num == 2){ // 출금
                            System.out.println("출금하실 계좌를 선택해주세요.");
                            accounts = AccountService.findAll(userId);
                            if(!accounts.isEmpty()){
                                Service.withDrawAccount(scanner, accounts);
                                continue;

                            }else{
                                System.out.println("계좌가 없습니다.");
                                continue;
                            }


                        }else if(transaction_num == 3){ // 송금
                            System.out.println("출금하실 계좌를 선택해주세요.");
                            accounts = AccountService.findAll(userId);
                            if(!accounts.isEmpty()){
                                Service.transAccount(scanner, accounts);
                                continue;
                            }else{
                                System.out.println("계좌가 없습니다.");
                                continue;
                            }


                        }else if(transaction_num == 4){ // 홈으로
                            continue;
                        }else{
                            System.out.println("잘못 입력하셨습니다.");
                            continue;
                        }

                    case "3":
                        System.out.println("로그아웃합니다.");
                        break;

                    case "0":
                        System.out.println("프로그램을 종료합니다.");
                        return;

                    default:
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }
                break;
            }
        }
    }
}

