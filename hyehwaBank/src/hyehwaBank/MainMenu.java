package hyehwaBank;

import java.util.Scanner;

public class MainMenu {
	String userName;
	int userIdx;
	
	public MainMenu(String name, int uid) {
		userName = name;
		userIdx = uid;
		
		run();
		
	}
	
	void run() {
		for (;;) {
			int menu = menu();
			if (menu == 0) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			if (menu == 1) {
				//계좌 개설
				System.out.println("계좌 개설xo");
				new CreateAccount(userIdx);
			}else if (menu == 2) {
				//전체 계좌 조회
				System.out.println("계좌 조회xo");
				new AccountMenu(userName, userIdx);
			}else if (menu == 3) {
				//고객 정보 출력
				System.out.println("고객 정보xx");
			}
		}
	}

	public int menu() {
		int menu = -1;
		while (true) {
			System.out.println();
			System.out.println("==============================");
			System.out.println("****hyehwa Bank Menu*****");
			System.out.println();
			System.out.println("1. 계좌 개설");
			System.out.println("2. 전체 계좌 조회");
			System.out.println("3. 고객 정보 출력");
			System.out.println("0. 프로그램 종료");
			System.out.println("==============================");
			System.out.println("메뉴를 선택해 주세요.");
			Scanner sc = new Scanner(System.in);
			try {
				menu = Integer.parseInt(sc.nextLine());
			}catch (NumberFormatException e){
				System.out.println("숫자를 입력해 주세요.");
				continue;
			}
			if (menu > 3) {
				System.out.println("올바른 숫자를 입력해 주세요.");
				continue;
			}
			return menu;
		
		}
	}
	
}
