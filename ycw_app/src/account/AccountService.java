package account;

import account.Account;
import account.AccountDao;
import trade.Trade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class AccountService {
    Scanner sc = new Scanner(System.in);

    final String driver = "com.mysql.cj.jdbc.Driver";
    final String url = "jdbc:mysql://practice-mysql.cvdyoyfefnzk.ap-northeast-2.rds.amazonaws.com"; // MySQL 서버 주소
    final String schema = "practice_db"; // MySQL DATABASE 이름
    final String userName= "admin"; //  MySQL 서버 아이디
    final String password = "Ucheol92!4"; // MySQL 서버 비밀번호
    AccountDao dao= new AccountDao();


    public void checkDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String insert() throws SQLException {
        Connection con = null;
        Account account = new Account();

        System.out.println("계좌 개설을 진행합니다.");
        String accountNum = "";
        System.out.print("계좌 소유자 계정: ");
        account.setUserId(sc.next());
        account.setProductType("예금");
        System.out.println(account.getUserId());

        checkDriver();
        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            ArrayList<Account> accountCheck = new ArrayList<>();

            // 계좌번호 난수 생성하고 중복체크, 중복시 while 문 루프
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


    public ArrayList<Account> selectAll() throws SQLException {
        Connection con = null;
        ArrayList<Account> list;

        System.out.println("----------------------------------");
        System.out.println("검색할 사용자의 계정 입력");
        System.out.print("ID: ");
        String userId = sc.nextLine();

        checkDriver();
        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            list = dao.selectAll(con, userId);
        } finally {
            if(con!=null) con.close();
        }
        return list;
    }


    public ArrayList<Account> selectOne() throws SQLException {
        Connection con = null;
        ArrayList<Account> list;

        System.out.println("검색할 계좌번호 입력");
        String accountNum = sc.nextLine();

        checkDriver();
        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            list = dao.selectOne(con, accountNum);
        } finally {
            if(con!=null) con.close();
        }
        return list;
    }


    public String delete() throws SQLException {
        Connection con = null;
        checkDriver();

        System.out.println("해지할 계좌번호 입력");
        String accountNum = sc.next();

        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
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
        String result;

        System.out.println(trade.getAction() + " 계좌 입력");
        String accountNum = sc.nextLine();

        checkDriver();
        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            result = dao.depositUpdate(con, trade);
        } finally {
            if(con!=null) con.close();
        }

        return result;
    }

}
