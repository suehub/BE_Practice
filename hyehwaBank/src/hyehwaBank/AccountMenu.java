package hyehwaBank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountMenu {
	String userName;
	int userIdx;
	
	String[] accountList;
	int cnt;
	
	public AccountMenu(String name, int uid) {
		userName = name;
		userIdx = uid;
		
		run();
	}
	
	void run() {
		//실행

			int menu = menu();
			if (menu == 0) {
				System.out.println("이전 페이지로 나갑니다.");
				
			}else {
			//계좌 메뉴로 가야함.
			new BankingMenu(accountList[cnt-1], userIdx);
			}
	}
	
	int menu() {
		int menu = -1;
		while(true) {
			System.out.println();
			System.out.println("==============================");
			System.out.println("*****"+userName+"님의 계좌 목록*****");
			getAccountNum();
			System.out.println("0. 이전 페이지로 나가기");
			System.out.println("==============================");
			System.out.println("숫자를 눌러 작업할 계좌 번호 선택하세요.");
			System.out.println();
			
			menu = inputData();
			
			if ((menu>cnt) && (cnt!=0)){
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
	
	void getAccountNum() {
		ResultSet result = null;
		DBDriver db = new DBDriver();
		db.connectDB();
		
		String getCntSql = "SELECT COUNT(*) FROM tbl_accounts WHERE uid="+userIdx+";";
		String getDataSql = "SELECT account_num FROM tbl_accounts WHERE uid=" +userIdx+ ";";
		
		cnt = db.CheckExist(getCntSql);
		accountList = new String[cnt];
		result = db.getData(getDataSql);
		
		int count = 1;
		String accountTemp;
		try {
			while(result.next()) {
				accountTemp = result.getString(1);
				System.out.printf("%d. 계좌번호 : %s\n", count, accountTemp);
				accountList[count-1] = accountTemp;
				count++;
			}
		}catch (SQLException e) {
			System.out.println("에러 "+e);
		}
		db.DBclose();
		
	}
		
}
