package src.service;

import src.db.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class AccountService {

    public static void createAccount(Scanner scanner) throws SQLException {
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.nextLine();

        Random random = new Random();
        String accountId = String.valueOf(10000 + random.nextInt(90000)); // 5자리 랜덤 숫자 생성
        PreparedStatement pstmt;
        String sql = "INSERT INTO Account (AccountId, balance, User_UserId) VALUES (?, 0, ?)";
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
            System.out.println(userId + "님의 계좌는 " + accountId + " 입니다.");
        } catch (SQLException se) {
            System.out.println("없는 ID 입니다.");

        }
//        finally {
//            try {
//                if (conn != null) conn.close();
//            } catch(SQLException se) {
//                se.printStackTrace();
//            }
//        }
    }

    public static void deleteAccount(Scanner scanner) throws SQLException {
        System.out.print("계좌 번호를 입력하세요: ");
        String accountId = scanner.nextLine();
        PreparedStatement pstmt;
        String sql = "DELETE FROM Account WHERE accountId = ? ";

        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            pstmt.executeUpdate();
            System.out.println("계좌가 삭제되었습니다.");
        } catch (SQLException se) {
            System.out.println("없는 계좌 입니다.");

        }
    }

    public static void deposit(Scanner scanner) throws SQLException {
        System.out.println("계좌 번호를 입력해주세요.");
        String accountId = scanner.nextLine();
        System.out.println("입금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();
        PreparedStatement pstmt;
        String sql = "UPDATE Account SET balance = balance + ? WHERE AccountId = ?";
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
        } catch(SQLException se) {
            System.out.println("잘못된 접근입니다.");
        } finally {
            insertHistory(accountId, "입금", amount);
        }

    }

    public static void withdraw(Scanner scanner) throws SQLException {
        System.out.println("사용자 ID 를 입력해주세요.");
        String userId = scanner.nextLine();
        System.out.println("계좌 번호를 입력해주세요.");
        String accountId = scanner.nextLine();
        System.out.println("출금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();
        PreparedStatement pstmt;
        String sql = "UPDATE Account SET balance = balance - ? WHERE AccountId = ?";
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
        }
        insertHistory(accountId, "출금", amount);
    }


    public static void transfer(Scanner scanner) throws SQLException {
//        conn.setAutoCommit(false);
        System.out.println("본인 계좌 번호를 입력해주세요.");
        String fromAccountId = scanner.nextLine();
        System.out.println("보낼 계좌 번호를 입력해주세요.");
        String toAccountId = scanner.nextLine();
        System.out.println("송금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();
        PreparedStatement pstmt;
        try {
            withdraw(fromAccountId, amount);
            deposit(toAccountId, amount);
//            conn.commit();
            insertHistory(fromAccountId, "송금", amount);
            insertHistory(toAccountId, "입금", amount);
        } catch (SQLException e) {
//            conn.rollback();
            throw e;
        } finally {
//            conn.setAutoCommit(true);
//        }
        }
    }

    private static void deposit(String toAccountId, int amount) throws SQLException {
        String sql = "UPDATE Account SET balance = balance + ? WHERE AccountId = ?";
        PreparedStatement pstmt;
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, toAccountId);
            pstmt.executeUpdate();
        }
    }

    private static void withdraw(String fromAccountId, int amount) throws SQLException {
        String sql = "UPDATE Account SET balance = balance - ? WHERE AccountId = ?";
        PreparedStatement pstmt;
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, fromAccountId);
            pstmt.executeUpdate();
        }
    }

    static void insertHistory(String accountId, String type, int amount) throws SQLException {
        String sql = "INSERT INTO history (Account_AccountId, type, balance, amount, time) VALUES (?, ?, (SELECT balance FROM Account WHERE AccountId = ?), ?, ?)";
        PreparedStatement pstmt;
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            pstmt.setString(2, type);
            pstmt.setString(3, accountId);
            pstmt.setInt(4, amount);
            pstmt.setString(5, time);
            pstmt.executeUpdate();
        }
    }
}
