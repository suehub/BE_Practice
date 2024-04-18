package hyehwaBank;

import java.sql.ResultSet;
import java.util.Scanner;

public class DepositMenu {
	String accountNum;
	public DepositMenu(String AccountNum) {
		//
		accountNum = AccountNum;
		run();
	}

	void run() {
		//실행
		int amount = menu();
		
		if (amount>0) {
			//입금
			depositMoney(amount);
		}else if(amount==0) {
			System.out.println("입금 서비스를 종료합니다.");
			System.out.println();
		}
	}
	
	int menu() {
		int amount;
		while(true) {
			System.out.println();
			System.out.println("==============================");
			System.out.println("****hyehwa Bank Deposit Menu*****");
			System.out.println();
			System.out.println("입금할 금액을 입력하세요.");
			System.out.println("0 입력시 종료.");
			amount = inputData();
			
			if (amount<0) {
				System.out.println("올바른 금액을 입력하세요.");
			}else {
				break;
			}
		}
		return amount;
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
	
	void depositMoney(int amount) {
		int balance;
		int after_balance;
		int result;
		DBDriver db = new DBDriver();
		db.connectDB();
		
		String getAccountInfoSql = "SELECT balance FROM tbl_accounts WHERE account_num='"+accountNum+"';";
		balance = db.CheckExist(getAccountInfoSql);
		
		int afterBalance = balance+amount;
	
		String updateAccountSql = "UPDATE tbl_accounts "
								+ "SET balance="+afterBalance+", "
								+ "updated_at = current_timestamp() "
								+ "WHERE account_num = '" +accountNum+ "';";
		String insertDepositSql = "INSERT tbl_deposit_withdrawal (aid, balance, amount, after_balance, tid)"
									+ "VALUES ((SELECT aid FROM tbl_accounts WHERE account_num = '"+accountNum+"'), "
									+ balance+ ", " +amount+ ", " +afterBalance+ ", 1);";
		db.updateData(updateAccountSql);
		db.insertData(insertDepositSql);
		
		System.out.println("계좌 '"+accountNum+"'의 잔액은 " +afterBalance+ "원입니다.");
		
	}
	
}
