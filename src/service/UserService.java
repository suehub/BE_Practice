package src.service;

import src.db.JdbcConnection;
import src.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static src.Exception.GlobalException.log;

public class UserService {
    public static void createUser(Scanner scanner) throws SQLException {
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.next();

        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.next();
        PreparedStatement pstmt;
        String sql = UserRepository.createUser();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("사용자가 생성되었습니다.");
        }catch(Exception e) {
            System.out.println("이미 존재하는 회원입니다.");
            log.severe("이미 존재한 회원");
//            GlobalException.logSevere("이미 존재한 회원");
        }
    }

    public static boolean findUser(String userId) throws SQLException {
        String sql = UserRepository.findUserId();
        try (Connection conn = JdbcConnection.JdbcConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, userId);
             ResultSet rs = pstmt.executeQuery();

             if (rs.next()) {
                return true;
             } else {
                 System.out.println("사용자가 존재하지 않습니다.");
                 return false;
             }
        } catch(Exception e) {
            log.severe("DB 접근 에러");
//            GlobalException.logSevere("DB 접근 에러");
            return false;
        }
    }


    public static boolean findUserPw(String userId, Scanner scanner) throws SQLException{
        System.out.println("비밀 번호를 입력하세요: ");
        String password = scanner.next();

        String sql = UserRepository.findUserPassword();
        try (Connection conn = JdbcConnection.JdbcConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("로그인 성공");
                return true;
            } else {
                System.out.println("비밀번호가 틀립니다.");
                return false;
            }
        } catch(Exception e) {
            log.severe("DB 접근 에러");
//            GlobalException.logSevere("DB 접근 에러");
            return false;
        }
    }
}
