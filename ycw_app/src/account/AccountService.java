package account;

import client.Status;
import user.UserDao;
import repository.DriverConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class AccountService {
    Scanner sc = new Scanner(System.in);
    DriverConnector driverConnector = new DriverConnector();
    AccountDao dao = new AccountDao();
    UserDao userDao = new UserDao();


    public Status insert(Status status) throws SQLException {
        Connection con = null;
        Account account = new Account();
        ArrayList<Account> accountList;
        String accountNum = "";

        con = driverConnector.connectDriver();
        try {
            // 계좌번호 난수 생성하고 중복체크, 중복시 루프
            do {
                for (int i = 0; i < 10; i++) {
                    accountNum += (int) ((Math.random() * (10 - 0)) + 0);
                }
                accountList = dao.selectOne(con, accountNum);
            } while (!accountList.isEmpty());

            // 생성할 계좌 정보 설정
            account.setUserId(status.getUserId());
            account.setaccountNum(accountNum);
            account.setProductType(status.getData());

            // 신규 계좌 insert
            status.setMessage(dao.insert(con, account));
            status.setWorkName("manage_accounts");
            status.setWorkFlow("continue");
            status.setData("");

            return status;
        } finally {
            if(con!=null) con.close();
        }
    }

    public ArrayList<Account> selectMyAllAccount(Status status) throws SQLException {
        Connection con = null;
        ArrayList<Account> accountList;

        con = driverConnector.connectDriver();
        try {
            accountList = dao.selectAll(con, status.getUserId());
        } finally {
            if(con!=null) con.close();
        }

        return accountList;
    }


//    public ArrayList<Account> selectMyAccount(Status) throws SQLException {
//        Connection con = null;
//        ArrayList<Account> accountList;
//
//        con = driverConnector.connectDriver();
//        try {
//            accountList = dao.selectOne(con, );
//        } finally {
//            if(con!=null) con.close();
//        }
//        return accountList;
//    }


    public Status delete(Status status) throws SQLException {
        Connection con = null;

        con = driverConnector.connectDriver();
        try {
            // 입력받은 계좌번호 유효성 검증
            ArrayList<Account> accountCheck = dao.selectOne(con, status.getData());

            if (accountCheck.isEmpty()) {
                status.setMessage("[Error] 존재하지 않는 계좌입니다.");
                return status;
            }
            // 해지할 계좌 delete
            status.setMessage(dao.delete(con, status.getData()));
            status.setWorkName("manage_accounts");
            status.setWorkFlow("continue");
            status.setData("");

            return status;
        } finally {
            if(con!=null) con.close();
        }
    }

//    public String updateOne(Trade trade) throws SQLException {
//        Connection con = null;
//        String resultMessage;
//
//        System.out.println(trade.getAction() + " 계좌 입력");
//        String accountNum = sc.nextLine();
//
//        con = driverConnector.connectDriver();
//        try {
//            resultMessage = dao.updateOne(con, trade,false);
//        } finally {
//            if(con!=null) con.close();
//        }
//
//        return resultMessage;
//    }

}
