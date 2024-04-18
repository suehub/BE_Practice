package src.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HistoryService {

    public static void getTransactionHistory(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("계좌 번호를 입력하세요: ");
        String accountId = scanner.nextLine();

        String sql = "SELECT * FROM history WHERE Account_AccountId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("거래 유형: " + rs.getString("type"));
                    System.out.println("잔액: " + rs.getInt("balance"));
                    System.out.println("금액: " + rs.getInt("amount"));
                    System.out.println("----------------------");
                }
            }
        }catch(SQLException se) {
            System.out.println("없는 계좌입니다.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
