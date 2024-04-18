package hyehwaBank;

import java.util.Scanner;

public class LoginMenu {
	String userName;
	String password;
	
	public LoginMenu() {
		run();
	}
	
	void run() {
		//실행
		menu();
		insertData();
	}
	
	void menu() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("==============================");
		System.out.println("****hyehwa Login Menu*****");
		System.out.println();
		System.out.println("이름을 입력하세요");
		userName = sc.nextLine();
		System.out.println("비밀번호를 입력하세요.");
		password = sc.nextLine();
		System.out.println();
		System.out.println("==============================");
	}
	
	void insertData() {
		int rs = -1;
		DBDriver db = new DBDriver();
		db.connectDB();
		
		String exist_sql = "SELECT IF(EXISTS("
				+"SELECT name, password FROM tbl_users "
				+"WHERE name='" +userName+ "' AND password='" +password+ "'),1,0);";
		rs = db.CheckExist(exist_sql);
		
		if (rs == 1) {
			//로그인
			String getUid_sql = "SELECT uid FROM tbl_users WHERE name='" +userName+ "';";
			//명칭 바꿔야 할듯
			int userIdx = db.CheckExist(getUid_sql);
			
			db.DBclose();
			new MainMenu(userName, userIdx);
		}else {
			System.out.println("다시 시도해 주세요.");
			db.DBclose();
		}
	}
}
