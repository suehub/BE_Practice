package hyehwaBank;

import java.util.Scanner;

public class WithdrawMenu {
	String accountNum;
	DBDriver db = new DBDriver();
	Scanner sc = new Scanner(System.in);
	
	public WithdrawMenu(String AccountNum) {
		//
		accountNum = AccountNum;
		db.connectDB();
		run();
		db.DBclose();
	}
	
	void run() {
		//
		int balance = getBalance(db, accountNum);
		
		for(;;) {
			int amount = menu();
			if (amount==0) {
				System.out.println("출금 서비스를 종료합니다.");
				System.out.println();
				break;
			}
			//비밀번호 확인
			if(amount>balance) {
				System.out.println("-----------------------------");
				System.out.println("잔액이 부족합니다.");
				System.out.println("-----------------------------");
				System.out.println("잔액 : "+balance);
			}else if(amount<=balance) {
				//출금
				withdrawMoney(db, amount, balance);
				break;
			}
		}
	}
	
	int menu() {
		int amount;
		boolean flag;
		while (true) {
			System.out.println();
			System.out.println("==============================");
			System.out.println("****hyehwa Bank Deposit Menu*****");
			System.out.println();
			System.out.println("출금할 금액을 입력하세요.");
			System.out.println("0 입력시 종료.");
			amount = inputData();
			
			if (amount<0) {
				System.out.println("올바른 금액을 입력하세요.");	
			}else if (amount==0){
				return amount;
			}else {
				System.out.println("계좌의 비밀번호를 입력해주세요.");
				//비밀번호 check
				flag = checkPassword(db, accountNum);
				if (flag==false) {
					System.out.println("비밀번호를 잘못 입력하셨습니다.");
				}else {
					return amount;
					
				}
			}
		}
	}
	
	int inputData() {
		int menu = -1;
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
	int getBalance(DBDriver db, String accountNum) {
		int balance;
		String getBalaceSql = "SELECT balance FROM tbl_accounts "
				+ "WHERE account_num ='" +accountNum+ "';";
		balance = db.CheckExist(getBalaceSql);
		return balance;
	}
	
	void withdrawMoney(DBDriver db,int amount, int balance) {
		int afterBalance = balance - amount;
		
		String updateAccountSql = "UPDATE tbl_accounts "
								+ "SET balance = " +afterBalance+ ", "
								+ "updated_at = current_timestamp() "
								+ "WHERE account_num = '" + accountNum+ "';";
		String insertWithdrawSql = "INSERT tbl_deposit_withdrawal (aid, balance, amount, after_balance, tid)"
				+ "VALUES ((SELECT aid FROM tbl_accounts WHERE account_num = '"+accountNum+"'), "
				+ balance+ ", " +amount+ ", " +afterBalance+ ", 2);";
		db.updateData(updateAccountSql);
		db.insertData(insertWithdrawSql);
		
		System.out.println("계좌 '"+accountNum+"'의 잔액은 " +afterBalance+ "원입니다.");
	}
	
	boolean checkPassword(DBDriver db, String accountNum) {
		String userPassword = sc.nextLine();
		String getPasswordSql = "SELECT password FROM tbl_accounts WHERE account_num='" +accountNum+ "';";
		String dbPassword = db.getDataResult(getPasswordSql);
		if (userPassword.equals(dbPassword)) {
			return true;
		}else {
			return false;
		}
		
	}
}
