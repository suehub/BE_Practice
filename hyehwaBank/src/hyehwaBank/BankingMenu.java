package hyehwaBank;

import java.util.Scanner;

public class BankingMenu {
	String accountNum;
	int userIdx;
	
	public BankingMenu(String AccountNum, int uid) {
		accountNum = AccountNum;
		userIdx = uid;
		//
		run();
	}
	
	void run() {
		//실행
		int menu = menu();
		if (menu==0) {
			System.out.println("이전 페이지로 나갑니다.");
		}else if (menu==1) {
			//입금
			System.out.println("계좌입금xo");
			new DepositMenu(accountNum);
		}else if (menu == 2) {
			//출금
			System.out.println("계좌출금xo");
			new WithdrawMenu(accountNum);
		}else if (menu ==3) {
			//계좌 이체
			System.out.println("계좌이체xx");
		}else if (menu==4) {
			//거래내역조회
			System.out.println("거래내역조회xx");
		}
	}
	
	int menu() {
		int menu = -1;
		while(true) {
			System.out.println("==============================");
			System.out.println("*****Hyehwa Banking Service*****");
			System.out.println("1. 입금");
			System.out.println("2. 출금");
			System.out.println("3. 계좌이체");
			System.out.println("4. 거래내역조회");
			System.out.println("0. 계좌 목록으로 나가기");
			System.out.println("==============================");
			System.out.println("메뉴를 선택해 주세요.");
			
			menu = inputData();
			
			if (menu>4) {
				System.out.println("올바른 숫자를 입력해주세요.");
			}else {
				break;
			}
		}
		return menu;
	}
	
	int inputData() {
		int menu = -1;
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				menu = Integer.parseInt(sc.nextLine());
				break;
			}catch (NumberFormatException e){
				System.out.println("숫자를 입력해 주세요.");
			}
		}
		return menu;
	}
}
