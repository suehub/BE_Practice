package account;

import trade.Trade;

import java.sql.*;
import java.util.ArrayList;

public class AccountDao {

    public ArrayList<Account> selectAll(Connection con, String userId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Account> list = new ArrayList<>();
        try {
            String sql = "select account_number, Users_user_id, product_type, balance from Accounts where Users_user_id = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String accountNum = rs.getString(1);
                String user_id = rs.getString(2);
                String productType = rs.getString(3);
                int balance = rs.getInt(4);
                list.add(new Account(accountNum, user_id, productType, balance));
            }
        } finally {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }
        return list;
    }

    public ArrayList<Account> selectOne(Connection con, String accountNum) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Account> list = new ArrayList<>();

        try {
            String sql = "select account_number, Users_user_id,product_type, balance  \n" +
                        "from practice_db.Accounts \n" +
                        "where account_number = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, accountNum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountNum = rs.getString(1);
                String userId = rs.getString(2);
                String productType = rs.getString(3);
                int balance = rs.getInt(4);
                list.add(new Account(accountNum, userId, productType, balance));
            }
        } finally {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }
        return list;
    }

    public String insert(Connection con, Account account) throws SQLException {
        PreparedStatement pstmt = null;
        boolean result = false;
        String resultMessage = "";
        try {
            String sql = "insert into Accounts values(?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, account.getaccountNum());
            pstmt.setString(2, account.getUserId());
            pstmt.setString(3, account.getProductType());
            pstmt.setInt(4, account.getBalance());
            result = pstmt.execute(); // 성공시 false, 실패시 true
            if (!result) {
                resultMessage = account.getaccountNum() + " " + account.getProductType() +" 계좌의 생성이 완료되었습니다.";
            } else {
                resultMessage = "계좌 개설에 실패하였습니다.";
            }
        } finally {
            con.close();
            if(pstmt!=null)pstmt.close();
        }
        return resultMessage;
    }

    public String delete (Connection con, String accountNum) throws SQLException {
        PreparedStatement pstmt = null;
        boolean result = false;
        String resultMessage = "";
        try {
            String sql = "delete from Accounts where account_number = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, accountNum);
            result = pstmt.execute(); // 성공시 false, 실패시 true
            if (!result) {
                resultMessage = accountNum + " 계좌가 삭제되었습니다.";
            } else {
                resultMessage = "계좌 삭제에 실패하였습니다.";
            }
        } finally {
            con.close();
            if(pstmt!=null) pstmt.close();
        }
        return resultMessage;
    }

    public String depositUpdate(Connection con, Trade trade) throws SQLException {
        PreparedStatement pstmt = null;
        boolean result;
        String resultMessage;

        try {
            String sql = "update Accounts set balance = ? where account_number = ?;";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trade.getBalance());
            pstmt.setInt(2,trade.getBalance());
            result = pstmt.execute();
            if (!result) {
                resultMessage = trade.getTargetAccount() + "에 " +
                                trade.getAmount() + "원" +
                                trade.getAction() +"되었습니다.";
            } else {
                resultMessage = trade.getAction() + "에 실패하였습니다.";
            }
        } finally {
            con.close();
            if(pstmt!=null) pstmt.close();
        }

        return resultMessage;
    }

}