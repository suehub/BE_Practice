package account;

import account.Account;
import account.AccountDao;
import trade.Trade;
import user.User;
import user.UserDao;
import repository.DriverConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class AccountService {
    Scanner sc = new Scanner(System.in);
    DriverConnector driverConnector = new DriverConnector();
    AccountDao dao = new AccountDao();
    UserDao userDao = new UserDao();


    public String insert() throws SQLException {
        Connection con = null;
        Account account = new Account();

        System.out.println("----------------------------------");
        System.out.println("           신규 계좌 개설");
        System.out.println("----------------------------------");
        String accountNum = "";
        System.out.print("- 계좌 소유자 계정: "); // 로그인 구현 후엔 userId에 자기 계정 자동 할당
        account.setUserId(sc.next());
        account.setProductType("예금");
        System.out.println(account.getUserId());

        con = driverConnector.connectDriver();
        try {
            ArrayList<Account> accountCheck = new ArrayList<>();

            // 계좌번호 난수 생성하고 중복체크, 중복시 루프
            do {
                for (int i = 0; i < 10; i++) {
                    accountNum += (int) ((Math.random() * (10 - 0)) + 0);
                }
                accountCheck = dao.selectOne(con, accountNum);
            } while (!accountCheck.isEmpty());

            account.setaccountNum(accountNum);
            String resultMessage = "";
            resultMessage = dao.insert(con, account);

            return resultMessage;
        } finally {
            if(con!=null) con.close();
        }
    }

    public String selectMyAllAccount() throws SQLException {
        Connection con = null;
        ArrayList<Account> accountlist;
        ArrayList<User> userList = new ArrayList<>();
        String resultMessage;
        String userId = ""; // 수정필요!!

        con = driverConnector.connectDriver();
        try {
            accountlist = dao.selectAll(con, userId);
        } finally {
            if(con!=null) con.close();
        }
        // 결과 메시지 생성
        resultMessage = "----------------------------------\n";
        resultMessage += ("\n      " + userId + "님의 계좌 내역\n");
        resultMessage += "\n----------------------------------";
        resultMessage += "\nno | 상품 |   계좌번호   | 잔액     ";
        resultMessage += "\n----------------------------------";

        Account myAccount = new Account();
        for (int i = 0; i < accountlist.size(); i++) {
            myAccount = accountlist.get(i);
            resultMessage += ("\n " + (i+1)
                             + " | " + myAccount.getProductType()
                             + " | " + myAccount.getaccountNum()
                             + " | " + myAccount.getBalance());
        }


        return resultMessage;
    }

    public ArrayList<Account> selectMyAccount() throws SQLException {
        Connection con = null;
        ArrayList<Account> accountList;

        System.out.println("----------------------------------");
        System.out.println("           거래내역 조회");
        System.out.println("----------------------------------");
        System.out.println("[input] 계좌번호: ");
        String accountNum = sc.next();

        con = driverConnector.connectDriver();
        try {
            accountList = dao.selectOne(con, accountNum);
        } finally {
            if(con!=null) con.close();
        }
        return accountList;
    }

    public String delete() throws SQLException {
        Connection con = null;

        System.out.println("해지할 계좌번호 입력");
        String accountNum = sc.next();

        con = driverConnector.connectDriver();
        try {
            ArrayList<Account> accountCheck = dao.selectOne(con, accountNum);
            String resultMessage = "";

            if (accountCheck.isEmpty()) {
                resultMessage = "존재하지 않는 계좌입니다.";
                return resultMessage;
            }

            resultMessage = dao.delete(con, accountNum);

            return resultMessage;
        } finally {
            if(con!=null) con.close();
        }
    }

    public String UpdateOne(Trade trade) throws SQLException {
        Connection con = null;
        String resultMessage;

        System.out.println(trade.getAction() + " 계좌 입력");
        String accountNum = sc.nextLine();

        con = driverConnector.connectDriver();
        try {
            resultMessage = dao.updateOne(con, trade,false);
        } finally {
            if(con!=null) con.close();
        }

        return resultMessage;
    }

}
