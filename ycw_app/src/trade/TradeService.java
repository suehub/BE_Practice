package trade;

import account.Account;
import account.AccountDao;
import user.User;
import user.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeService {

    Scanner sc = new Scanner(System.in);

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://practice-mysql.cvdyoyfefnzk.ap-northeast-2.rds.amazonaws.com"; // MySQL 서버 주소
    String schema = "practice_db"; // MySQL DATABASE 이름
    String userName = "admin"; //  MySQL 서버 아이디
    String password = "Ucheol92!4"; // MySQL 서버 비밀번호
    AccountDao accountDao = new AccountDao();
    TradeDao dao = new TradeDao();

    public void checkDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String insert(int selectNum) throws SQLException {
        Connection con = null;
        Trade trade = new Trade();
        ArrayList<Account> requestList = new ArrayList<>();
        ArrayList<Account> targetList = new ArrayList<>();
        Boolean infoError = false;
        int tarNewBalance;
        int reqNewBalance;

        switch (selectNum) {
            case 6 -> trade.setAction("입금");
            case 7 -> trade.setAction("출금");
            case 8 -> trade.setAction("송금");
        }

        do {

            System.out.println("----------------------------------");
            System.out.println("          "+ trade.getAction() +" 정보 입력");
            System.out.println("----------------------------------");
            System.out.print("거래 ID: ");
            trade.setTradeId(sc.nextInt());
            System.out.print("입금계좌: ");
            switch (trade.getAction()) {
                case "입금", "출금" -> {
                    trade.setRequestAccount("-");
                }
                case "송금" -> {
                    System.out.print("출금 계좌: ");
                    trade.setRequestAccount(sc.next());
                    System.out.print("송금 계좌: ");
                }
            }
            trade.setTargetAccount(sc.nextLine());
            System.out.print("거래 금액: ");
            trade.setAmount(sc.nextInt());
            System.out.println("----------------------------------");

            // 계좌 잔액조회
            if (trade.getAction() == "송금") {
                requestList = accountDao.selectOne(con, trade.getRequestAccount());
            }
            targetList = accountDao.selectOne(con, trade.getTargetAccount());

            // 입력값 검증
            if (targetList.isEmpty()) {
                System.out.println("[Error] 올바른 계좌번호를 입력하세요.");
                infoError = true;
                continue;
            } else if (trade.getAction() == "송금" && targetList.isEmpty()) {
                System.out.println("[Error] 올바른 출금 계좌번호를 입력하세요.");
                infoError = true;
                continue;
            } else if (trade.getAction() == "송금" && requestList.isEmpty()) {
                System.out.println("[Error] 올바른 송금 계좌번호를 입력하세요.");
                infoError = true;
                continue;
            }

            if (trade.getAction() == "입금") {
                tarNewBalance = targetList.getFirst().getBalance() + trade.getAmount();
                trade.setBalance(tarNewBalance);
            } else if (trade.getAction() == "출금"){
                tarNewBalance = targetList.getFirst().getBalance() - trade.getAmount();
                if (tarNewBalance < 0) {
                    System.out.println("출금액이 보유잔고를 초과합니다.");
                    infoError = true;
                    continue;
                }
                trade.setBalance(tarNewBalance);
            } else if (trade.getAction() == "송금"){
                reqNewBalance = requestList.getFirst().getBalance();
                tarNewBalance = targetList.getFirst().getBalance();

                if (reqNewBalance < 0) {
                   System.out.println("송금액이 보유잔고를 초과합니다.");
                    infoError = true;
                    continue;
                }
                trade.setBalance(tarNewBalance);
            }

        } while (!infoError);

        String resultMessage;
        checkDriver();
        try {
            con = DriverManager.getConnection(url + "/" + schema, userName, password);

            resultMessage = dao.insert(con, trade);
            return resultMessage;
        } finally {
            if(con!=null) con.close();
        }
    }
}
