package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class AccountService {

    public static void createAccount(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.nextLine();

        Random random = new Random();
        String accountId = String.valueOf(10000 + random.nextInt(90000)); // 5자리 랜덤 숫자 생성

        String sql = "INSERT INTO Account (AccountId, balance, User_UserId) VALUES (?, 0, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
            System.out.println(userId +"님의 계좌는 "+ accountId+" 입니다.");
        }catch(SQLException se) {
            System.out.println("없는 ID 입니다.");

        } finally {
            try {
                if (conn != null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void deleteAccount(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("계좌 번호를 입력하세요: ");
        String accountId = scanner.nextLine();

        String sql = "DELETE FROM Account WHERE accountId = ? ";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId);
            pstmt.executeUpdate();
            System.out.println("계좌가 삭제되었습니다.");
        }catch(SQLException se) {
            System.out.println("없는 계좌 입니다.");

        } finally {
            try {
                if (conn != null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void deposit(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("계좌 번호를 입력해주세요.");
        String accountId = scanner.nextLine();
        System.out.println("입금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();

        String sql = "UPDATE Account SET balance = balance + ? WHERE AccountId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
        }
        insertHistory(conn, accountId, "입금", amount);
    }

    public static void withdraw(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("사용자 ID 를 입력해주세요.");
        String userId = scanner.nextLine();
        System.out.println("계좌 번호를 입력해주세요.");
        String accountId = scanner.nextLine();
        System.out.println("입금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();

        String sql = "UPDATE Account SET balance = balance - ? WHERE AccountId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
        }
        insertHistory(conn, accountId, "출금", amount);
    }


    public static void transfer(Connection conn, Scanner scanner) throws SQLException {
        conn.setAutoCommit(false);
        System.out.println("본인 계좌 번호를 입력해주세요.");
        String fromAccountId = scanner.nextLine();
        System.out.println("보낼 계좌 번호를 입력해주세요.");
        String toAccountId = scanner.nextLine();
        System.out.println("송금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();

        try {
            withdraw(conn, fromAccountId, amount);
            deposit(conn, toAccountId, amount);
            conn.commit();
            insertHistory(conn, fromAccountId, "송금", amount);
            insertHistory(conn, toAccountId, "입금", amount);
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static void deposit(Connection conn, String toAccountId, int amount) throws SQLException {
        String sql = "UPDATE Account SET balance = balance + ? WHERE AccountId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, toAccountId);
            pstmt.executeUpdate();
        }
        insertHistory(conn, toAccountId, "입금", amount);
    }

    private static void withdraw(Connection conn, String fromAccountId, int amount) throws SQLException {
        String sql = "UPDATE Account SET balance = balance - ? WHERE AccountId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, fromAccountId);
            pstmt.executeUpdate();
        }
        insertHistory(conn, fromAccountId, "출금", amount);
    }

    static void insertHistory(Connection conn, String accountId, String type, int amount) throws SQLException {
        String sql = "INSERT INTO history (Account_AccountId, type, balance, amount) VALUES (?, ?, (SELECT balance FROM Account WHERE AccountId = ?), ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, accountId);
            pstmt.setInt(3, amount);
            pstmt.executeUpdate();
        }
    }

}
