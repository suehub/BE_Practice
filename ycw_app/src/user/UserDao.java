package user;

import java.sql.*;
import java.util.ArrayList;
import repository.Query;

public class UserDao {

    public ArrayList<User> selectAll(Connection con) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<User> userlist = new ArrayList<>();
        try {
            String sql = "select user_id, name from Users";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString(1);
                String name = rs.getString(2);
                userlist.add(new User(userId, name));
            }
        } finally {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }
        return userlist;
    }

    public ArrayList<User> selectLogIn(Connection con, String userId, String password) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<User> userlist = new ArrayList<>();
        try {
            String sql = Query.USER_LOG_IN.getQueryString();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString(1);
                String name = rs.getString(2);
                userlist.add(new User(userId, name));
            }
        } finally {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }
        return userlist;
    }


    public ArrayList<User> selectOne(Connection con, String userId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<User> userlist = new ArrayList<>();
        try {
            String sql = Query.USER_SELECT.getQueryString();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString(1);
                String name = rs.getString(2);
                userlist.add(new User(userId, name));
            }
        } finally {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }
        return userlist;
    }

    public String insert(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = null;
        boolean result;
        String resultMessage;
        try {
            String sql = Query.USER_INSERT.getQueryString();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getuserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3,user.getName());
            result = pstmt.execute(); // 성공시 false, 실패시 true
            if (!result) {
                resultMessage = "[Info] " +user.getuserId() + "님의 가입이 완료되었습니다.";
            } else {
                resultMessage = "[Error] 회원가입에 실패하였습니다.";
            }
        } finally {
            con.close();
            if(pstmt!=null) pstmt.close();
        }
        return resultMessage;
    }

}