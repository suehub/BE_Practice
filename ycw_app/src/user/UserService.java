package user;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import client.Status;
import repository.DriverConnector;
import controller.*;



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
                status.setMessage(Message.ERROR_EXIST_USER.getMessage());
                return status;
            }
            status.setMessage(dao.insert(con, user));
            status.setUserId(Flow.OLD_GUEST.getFlow());
            status.setWorkName(Tag.LOG_IN);
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
                status.setMessage(Message.INFO_SUCCESS_LOGIN.getMessage(userList.getFirst().getName()));

                status.setWorkName(Tag.MAIN);
            } else if (userList.isEmpty()){
                status.setMessage(Message.ERROR_LOGIN_FAILED.getMessage());
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