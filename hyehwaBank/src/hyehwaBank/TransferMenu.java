package hyehwaBank;

import java.util.Scanner;

public class TransferMenu {
	Scanner sc = new Scanner(System.in);
	DBDriver db = new DBDriver();
	final String accountNum;
	
	public TransferMenu(String AccountNum) {
		accountNum = AccountNum;
		db.connectDB();
		run();
		
	}
	
	
	void run() {
		//실행
		boolean exit;
		for (;;) {
			int menu = menu();
			if (menu==0) {
				System.out.println("계좌이체 서비스를 종료합니다.");
				System.out.println();
				break;
			}else if(menu==1){
				exit = new transferMenu();
				if (exit==true) {
					break;
				}
			}
			
		}
	}
	
	int menu() {
		int menu;
		while(true) {
			System.out.println();
			System.out.println("==============================");
			System.out.println("****hyehwa Bank Deposit Menu*****");
			System.out.println();
			System.out.println("계좌 이체를 진행하시겠습니까?");
			System.out.println("1.YES, 0.NO");
			menu = inputData();
			
			if ((menu!=0)||(menu!=1)) {
				System.out.println("올바른 번호를 입력해주세요.");
			}else {
				return menu;
			}
		}
	}
	
	boolean transferMenu() {
		String receiveAccount;
		int rs;
		int retry;
		int amount;
		int balance;
		String password;
		boolean flag;
		
		while(true) {
			boolean exit = false;
			System.out.println("이체 받는 사람의 계좌번호를 입력해주세요.");
			receiveAccount = sc.nextLine();
			
			String checkAccountSql = "SELECT IF(EXISTS("
					+ "SELECT account_num FROM tbl_accounts "
					+ "WHERE account_num ='" +receiveAccount+ "'),1,0);";
			rs = db.CheckExist(checkAccountSql);
			if (rs ==0) {
				System.out.println("입력하신 계좌번호가 존재하지 않습니다.");
				System.out.println("다시 입력하시겠습니까?");
				System.out.println("1. YES, 2. NO");
				retry = Integer.parseInt(sc.nextLine());
				if (retry==0) {
					System.out.println("계좌이체 서비스가 종료됩니다.");
					break;
				}
			}else if(rs == 1){
				System.out.println("보낼 금액을 입력하세요.");
				amount = Integer.parseInt(sc.nextLine());
				System.out.println("계좌 비밀번호를 입력하세요.");
				password = sc.nextLine();
				
				//
				flag = checkPassword(db, accountNum);
				if (flag==false) {
					System.out.println("비밀 번호를 잘못 입력하셨습니다. 다시 입력해주세요.");
					break;
				}
				
				balance = getBalance(db, accountNum);
				if (amount ==0) {
					System.out.println("금액을 잘못입력하셨습니다.");
					break;
				}else if (balance<amount) {
					System.out.println("잔액이 부족합니다.");
					break;
				}else {
					//입출금....
					new InsertTranferData(receiveAccount, amount, balance);
					exit = true;
					
				}
			}
			return exit;
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
	
	 int[] depositMoney(String receiveAccount, int amount) {
		//상대...
		int[] result = new int[2];
		
		int balance;
		int afterBalance;
		
		String getAccountInfoSql = "SELECT balance FROM tbl_accounts WHERE account_num='"+receiveAccount+"';";
		balance = db.CheckExist(getAccountInfoSql);
		
		afterBalance = balance+amount;
	
		String updateAccountSql = "UPDATE tbl_accounts "
								+ "SET balance="+afterBalance+", "
								+ "updated_at = current_timestamp() "
								+ "WHERE account_num = '" +receiveAccount+ "';";

		db.updateData(updateAccountSql);
		result[0] = balance;
		result[1] = afterBalance;
		
		return result;
	}
	
	int withdrawMoney(int amount, int balance) {
		//내계좌
		int afterBalance = balance - amount;
		
		String updateAccountSql = "UPDATE tbl_accounts "
								+ "SET balance = " +afterBalance+ ", "
								+ "updated_at = current_timestamp() "
								+ "WHERE account_num = '" + accountNum+ "';";
		db.updateData(updateAccountSql);
		
		return afterBalance;
	}
	
	void insertTransferData(String receiveAccount, int amount, int balance) {
		int[] receiver;
		int sentAfterBalance;
		receiver = depositMoney(accountNum, amount);
		sentAfterBalance = withdrawMoney(amount, balance);
		
		String updateTranferSql = "INSERT INTO tbl_transer (sent_aid, amount, sent_balance, sent_after_balance, receive_aid, receive_balance, receive_after_balance) "
				 				+ "VALUES (" + "(SELECT aid FROM tbl_account WHERE account_num='" +accountNum+ "'), "
				 						+amount+ ", " +balance+ ", " +sentAfterBalance+ ", "
				 					+ "(SELECT aid FROM tbl_account WHERE account_num='" +receiveAccount+ "), "
				 					+ receiver[0] + ", " + receiver[1] + ");";
		db.insertData(updateTranferSql);
		System.out.println("계좌 이체가 완료되었습니다.");
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
