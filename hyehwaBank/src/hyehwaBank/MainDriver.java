package hyehwaBank;

import java.util.Scanner;

public class MainDriver {

	public MainDriver() {
		loginRun();
	}
	
	public void loginRun() {
		for (;;) {
			int loginMenu = loginMenu();
			
			if (loginMenu == 0) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			if (loginMenu == 1) {
				//로그인 클래스
				new LoginMenu();
				
			} else if (loginMenu == 2) {
				//회원가입 클래스
				System.out.println("회원가입 단계xo");
				new CreateUser();
			}
		}
	}

	// 첫 번째 화면 메뉴
	int loginMenu() {
		int loginMenu = -1;
		while (true) {
			System.out.println();
			System.out.println("==============================");
			System.out.println("****hyehwa Bank Login*****");
			System.out.println();
			System.out.println("1. 로그인");
			System.out.println("2. 아직 계좌가 없다면,,,");
			System.out.println("0. 프로그램 종료");
			System.out.println("==============================");
			System.out.println("메뉴를 선택해 주세요.");
			Scanner sc = new Scanner(System.in);
			try {
				loginMenu = Integer.parseInt(sc.nextLine());
			}catch (NumberFormatException e){
				System.out.println("숫자를 입력해 주세요.");
				continue;
			}
			
			if (loginMenu > 2) {
				System.out.println("올바른 숫자를 입력해주세요.");
				continue;
			}
		
			return loginMenu;
		}
	}
}
