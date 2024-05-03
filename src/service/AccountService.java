package src.service;

import src.Exception.GlobalException;
import src.db.JdbcConnection;
import src.repository.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

import static src.Exception.GlobalException.log;

public class AccountService {
    public static void createAccount(String userId) {

        Random random = new Random();
        String accountId = "";
        while(true){
            accountId = String.valueOf(10000 + random.nextInt(90000)); // 5자리 랜덤 숫자 생성
            if(!findOne(accountId)){
                break;
            }
        }

        PreparedStatement pstmt;
        String sql = AccountRepository.createAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
            System.out.println(userId+"님의 계좌가 생성되었습니다.  \n" +
                    "생성된 계좌번호는 "+ accountId+" 입니다.");;
        } catch (Exception e) {
            System.out.println("없는 ID 입니다.");
            log.severe("없는 ID");
        }
    }

    public static void deleteAccount(String accountId){
        PreparedStatement pstmt;
        String sql = AccountRepository.deleteAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            pstmt.executeUpdate();
            System.out.println("계좌가 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("없는 계좌 입니다.");
            log.severe("없는 계좌");
        }
    }

    public static void deposit(Scanner scanner){
        System.out.println("계좌 번호를 입력해주세요.");
        String accountId = scanner.next();
        if(!findOne(accountId)){
            System.out.println("없는 계좌입니다.");
            return;
        }
        System.out.println("입금 하실 금액을 입력해주세요.");
        int amount = scanner.nextInt();
        if(amount <= 0){
            System.out.println("0원 이하는 거래가 불가능합니다.");
            return;
        }
        PreparedStatement pstmt;
        String sql = AccountRepository.depositAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
            System.out.println("입금되었습니다.");
        } catch(Exception e) {
//            System.out.println("없는 계좌");
            log.severe("DB 접근 에러");
        } finally {
            insertHistory(accountId, "입금", amount);
        }

    }
    public static void deposits(String accountId, int amount){

        if(!findOne(accountId)){
            System.out.println("없는 계좌입니다.");
            return;
        }

        if(amount <= 0){
            System.out.println("0원 이하는 거래가 불가능합니다.");
            return;
        }
        PreparedStatement pstmt;
        String sql = AccountRepository.depositAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
            System.out.println("입금되었습니다.");
        } catch(Exception e) {
//            System.out.println("없는 계좌");
            log.severe("DB 접근 에러");
        } finally {
            insertHistory(accountId, "입금", amount);
        }

    }

    public static boolean withdraw(Scanner scanner, String accountId) {
        System.out.println("출금 하실 금액을 입력해주세요.");
        int amount = 0;
        try {
            amount = scanner.nextInt();

        }catch (Exception e){
            log.severe("금액 문자 입력");
            scanner.nextLine();
        }

        int balance = balance(accountId);
        if(amount <= 0){
            System.out.println("0원 이하는 거래가 불가능합니다.");
            return false;
        }
        if(amount > balance){
            System.out.println("출금 금액이 잔액을 초과했습니다.");
            return false;
        }
        PreparedStatement pstmt;
        String sql = AccountRepository.withdrawAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
        } catch(Exception e) {
            log.severe("DB 접근 에러");
        }
        insertHistory(accountId, "출금", amount);
        return true;
    }

    public static boolean withdraw(int amount, String accountId) {

        int balance = balance(accountId);
        if(amount <= 0){
            System.out.println("0원 이하는 거래가 불가능합니다.");
            return false;
        }
        if(amount > balance){
            System.out.println("출금 금액이 잔액을 초과했습니다.");
            return false;
        }
        PreparedStatement pstmt;
        String sql = AccountRepository.withdrawAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, accountId);
            pstmt.executeUpdate();
        } catch(Exception e) {
            log.severe("DB 접근 에러");
        }
        insertHistory(accountId, "출금", amount);
        return true;
    }

    public static int balance(String accountId) {
        PreparedStatement pstmt;
        String sql = AccountRepository.balanceAccount();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("balance");
                } else {
                    throw new Exception("계좌가 존재하지 않습니다.");
                }
            } catch(Exception e) {
                log.severe(e.getMessage());
            }
        }  catch(Exception e) {
            log.severe("DB 접근 에러");
        }
        return 0;
    }



    public static boolean transfer(Scanner scanner, String fromAccountId) {
//        conn.setAutoCommit(false);
        System.out.println("보낼 계좌 번호를 입력해주세요.");
        String toAccountId = scanner.next();
        if(!findOne(toAccountId)){
            System.out.println("없는 계좌입니다.");
            return false;
        }
        if(fromAccountId.equals(toAccountId)){
            System.out.println("같은 계좌로는 송금할 수 없습니다.");
            return false;
        }
        System.out.println("송금 하실 금액을 입력해주세요.");

        int amount = 0;
        try {
            amount = scanner.nextInt();

        }catch (Exception e){
            log.severe("송금 금액 오류");
            scanner.nextLine();
        }

        int balance = balance(fromAccountId);
        if(amount <= 0){
            System.out.println("0원 또는 문자는 거래가 불가능합니다.");
            return false;
        }
        if(amount > balance){
            System.out.println("출금 금액이 잔액을 초과했습니다.");
            return false;
        }


        PreparedStatement pstmt;
        try {
            withdraw(fromAccountId, amount);
            deposit(toAccountId, amount);
//            conn.commit();
            insertHistory(fromAccountId, "송금", amount);
            insertHistory(toAccountId, "입금", amount);
        }  catch(Exception e) {
            log.severe("송금 에러");
        }
        return true;
    }

    private static void deposit(String toAccountId, int amount) {
        String sql = AccountRepository.depositAccount();
        PreparedStatement pstmt;
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, toAccountId);
            pstmt.executeUpdate();
        } catch(Exception e) {
            log.severe("입금 에러");
        }
    }

    private static void withdraw(String fromAccountId, int amount) {
        String sql = AccountRepository.withdrawAccount();

        PreparedStatement pstmt;
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, fromAccountId);
            pstmt.executeUpdate();
        } catch(Exception e) {
            log.severe("출금 에러");
        }
    }

    static void insertHistory(String accountId, String type, int amount) {
        String sql = AccountRepository.historyAccount();
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
        } catch(Exception e) {
            log.severe("입출금 내역 에러");
        }
    }

    public static List<String> findAll(String userId) {
        PreparedStatement pstmt;
        List<String> list = new ArrayList<>();
        String sql = AccountRepository.findAll();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String s = rs.getString("AccountId");
                    list.add(s);
                    String bal = rs.getString("balance");
                    System.out.println("----------------------");
                    System.out.println(list.size()+". "+s+" 잔액 : "+ bal);
                }
            }
        } catch(SQLException se) {
            System.out.println("없는 계좌입니다.");
        }catch(Exception e) {
            log.severe("DB 접근 에러");
        }
        return list;
    }


    public static boolean findOne(String AccountId) {
        PreparedStatement pstmt;
        String sql = AccountRepository.findOne();
        try (Connection conn = JdbcConnection.JdbcConnection()) {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, AccountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return true;
                }else {
                    return false;
                }
            }
        }catch(SQLException se) {
            System.out.println("없는 계좌입니다.");
        }catch(Exception e) {
            log.severe("DB 접근 에러");
        }

        return false;
    }

}
