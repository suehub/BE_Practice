package hyehwaBank;

import java.util.Scanner;

public class CreateUser {
	String userName;
	String email;
	String password;
	
	public CreateUser() {
		int rs = -1;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-----------------------------");
		System.out.println("계정를 생성하기 위한 정보를 입력해주세요.");
		System.out.println("이름을 입력해 주세요.");
		userName = sc.nextLine();
		System.out.println("이메일 주소를 입력해주세요.");
		email = sc.nextLine();
		System.out.println("계정 비밀번호를 입력해주세요.(10자 이내)");
		password = sc.nextLine();
		
		DBDriver db = new DBDriver();
		db.connectDB();
		
		String exist_sql = "SELECT IF(EXISTS("
				+ "SELECT name, email FROM tbl_users "
				+ "WHERE name='" +userName+ "' AND "
				+ "email='" +email+ "'),1,0);";
		
		rs = db.CheckExist(exist_sql);
		
		if (rs == 1) {
			System.out.println("이미 존재하는 계정입니다.");
		}else if (rs == -1){
			System.out.println("다시 시도해 주세요");
		}else if (rs == 0) {
			String insert_sql = "INSERT INTO tbl_users (name, password, email)"
					+ "VALUES ('" +userName+ "', '"+password+ "', '" +email+ "');";
			db.insertData(insert_sql);
			System.out.println("계정 생성이 완료되었습니다.");
			System.out.printf("이름 : %s, 이메일 : %s, 비밀번호 : %s\n", userName, email, password);
		}
		
		String getUserIdxSql = "SELECT uid FROM tbl_users WHERE name='"+userName+"' AND email='"+email+"';";
		
		int userIdx = db.CheckExist(getUserIdxSql);
		db.DBclose();
		new CreateAccount(userIdx);
			
	}
}
