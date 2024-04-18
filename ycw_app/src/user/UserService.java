package user;

import java.sql.*;
import java.util.ArrayList;
import user.User;
import user.UserDao;
import java.util.Scanner;

public class UserService {

    Scanner sc = new Scanner(System.in);

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://practice-mysql.cvdyoyfefnzk.ap-northeast-2.rds.amazonaws.com"; // MySQL 서버 주소
    String schema = "practice_db"; // MySQL DATABASE 이름
    String userName = "admin"; //  MySQL 서버 아이디
    String password = "Ucheol92!4"; // MySQL 서버 비밀번호
    UserDao dao = new UserDao();

    public ArrayList<User> selectAll() throws SQLException {
        Connection con = null;
        ArrayList<User> list = null;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            list = dao.selectAll(con);
        } finally {
            if(con!=null) con.close();
        }
        return list;
    }

    public ArrayList<User> selectOne() throws SQLException {
        Connection con = null;
        ArrayList<User> list = null;
        System.out.println("검색할 사용자의 계정 입력");
        String userId = sc.nextLine();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            list = dao.selectOne(con, userId);
        } finally {
            if(con!=null) con.close();
        }
        return list;
    }

    public String insert() throws SQLException {
        Connection con = null;
        User user = new User();

        System.out.println("신규 가입 정보 입력");
        System.out.print("\nid : ");
        user.setuserId(sc.next());
        System.out.print("\npw : ");
        user.setPassword(sc.next());
        System.out.print("\nname: ");
        user.setName(sc.next());

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);
            ArrayList<User> userCheck = dao.selectOne(con, user.getuserId());
            String resultMessage = "";

            if (!userCheck.isEmpty()){
                resultMessage = "이미 가입된 회원입니다.";
                return resultMessage;
            }

            resultMessage = dao.insert(con, user);
            return resultMessage;
        } finally {
            if(con!=null) con.close();
        }
    }
}