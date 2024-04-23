package user;

import java.sql.*;
import java.util.ArrayList;

import client.Status;
import repository.DriverConnector;

import java.util.Scanner;

public class UserService {

    Scanner sc = new Scanner(System.in);
    DriverConnector driverConnector = new DriverConnector();
    UserDao dao = new UserDao();


    public Status signUp(Status status, User user) throws SQLException {
        Connection con = null;
        String resultMessage;

        // SQL 실행
        con = driverConnector.connectDriver();
        try {
            ArrayList<User> userCheck = dao.selectOne(con, user.getuserId());
            if (!userCheck.isEmpty()){
                status.setMessage("[Error] 이미 가입된 회원입니다.");
                return status;
            }
            status.setMessage(dao.insert(con, user));
            status.setUserId("signed_guest");
            status.setWorkName("log_in");
        } finally {
            if(con!=null) con.close();
        }
        return status;
    }


    public Status logIn(Status status, User user) throws SQLException {
        Connection con = null;
        ArrayList<User> userList;

        // SQL 실행
        con = driverConnector.connectDriver();
        try {
            userList = dao.selectLogIn(con, user.getuserId(), user.getPassword());
            if (!userList.isEmpty()){
                status.setUserId(userList.getFirst().getuserId());
                status.setMessage("[Info] " + userList.getFirst().getName() + "님 환영합니다.");
                status.setWorkName("main");
            } else if (userList.isEmpty()){
                status.setMessage("[Error] ID와 비밀번호를 확인하세요.");
            }
        } finally {
            if(con!=null) con.close();
        }

        return status;
    }


    public User selectOne(String userId) throws SQLException {
        Connection con = null;
        ArrayList<User> userList;

        // SQL 실행
        con = driverConnector.connectDriver();
        try {
            userList = dao.selectOne(con, userId);
        } finally {
            if(con!=null) con.close();
        }

        return userList.getFirst();
    }


/*    public ArrayList<User> selectAll() throws SQLException {
        Connection con = null;
        ArrayList<User> list;

        // SQL 실행
        con = driverConnector.connectDriver();
        try {
            list = dao.selectAll(con);
        } finally {
            if(con!=null) con.close();
        }
        return list;
    }*/


}