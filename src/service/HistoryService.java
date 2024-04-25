package src.service;

import src.Exception.GlobalException;
import src.db.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HistoryService {

    public static void getTransactionHistory(String accountId) {
        PreparedStatement pstmt;
        String sql = "SELECT * FROM history WHERE Account_AccountId = ?";
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("----------------------");
                    System.out.println("거래 유형: " + rs.getString("type"));
                    System.out.println("금액: " + rs.getInt("amount"));
                    System.out.println("잔액: " + rs.getInt("balance"));
                    System.out.println("시간: " + rs.getString("time"));
                    System.out.println("----------------------");
                }
            }
        }catch(Exception e) {
            System.out.println("없는 계좌입니다.");
            GlobalException.log(e);
        }
    }
}
