package hyehwaBank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class CreateAccount {
	String AccountNum;
	String password;
	
	public CreateAccount(int uid) {
		int userIdx = uid;
		ResultSet result = null;
		int rs = -1;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("-----------------------------");
		System.out.println("계좌를 생성하시겠습니까?.");
		System.out.println("1.yes, 2.no");
		int answer = Integer.parseInt(sc.nextLine());
		
		if (answer ==1) {
			DBDriver db = new DBDriver();
			db.connectDB();
	
			//계좌 생성
			System.out.println("계좌 비밀번호를 입력해주세요. (숫자4개)");
			password = sc.nextLine();
			//password 적절한지 확인 필요
			
			boolean flag = true;
			while(flag) {
				rs = -1;
				AccountNum = CreateAccountNum();
				String exist_sql = "SELECT IF(EXISTS("
						+"SELECT account_num FROM tbl_accounts "
						+ "WHERE account_num = '" +AccountNum+ "'),1,0);";
				
				rs = db.CheckExist(exist_sql);
				if (rs == 0) {
					break;
				}
			}
			
			String insert_sql = "INSERT INTO tbl_accounts (uid, account_num, password)"
					+ "VALUES (" +userIdx+ ", '" +AccountNum+ "', '" +password+ "');";
			db.insertData(insert_sql);
			db.DBclose();
			
			System.out.println("계좌 생성이 완료되었습니다.");
			System.out.println("계좌 번호는 '" +AccountNum+ "' 입니다.");
			
		}else if (answer == 2) {
			System.out.println("계좌 생성을 취소하셨습니다. 이전 화면으로 돌아갑니다.");
		}else {
			System.out.println("올바른 번호를 입력해주세요.");
		}
		
		
		
	}
	public String CreateAccountNum() {
		String accNum = "182"+"0245";
		Random random = new Random();
		int test = (int)(random.nextFloat()*10000);
		String lastNum = Integer.toString(test);
		accNum += lastNum;
		return accNum;
	}

}
