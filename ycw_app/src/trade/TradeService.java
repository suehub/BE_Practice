package trade;

import account.Account;
import account.AccountDao;
import page.PagePrinter;
import repository.DriverConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeService {

    Scanner sc = new Scanner(System.in);
    DriverConnector driverConnector = new DriverConnector();
    AccountDao accountDao = new AccountDao();
    TradeDao dao = new TradeDao();

    public String insert(int selectNum) throws SQLException {
        Connection con = null;
        Trade trade = new Trade();

        // 거래정보 입력 루프
        trade = PagePrinter.inputTradeInfo(selectNum);

        // SQL 실행
        String resultMessage;

        try {
            resultMessage = dao.insert(con, trade);
            System.out.println(resultMessage);
            resultMessage = accountDao.updateOne(con,trade,false);
            return resultMessage;
        } finally {
            if(con!=null) con.close();
        }
    }

    public String transfer(int selectNum) throws SQLException {
        Connection con = null;
        Trade trade = new Trade();
        boolean infoError = true;
        ArrayList<Account> requestList = new ArrayList<>();
        ArrayList<Account> targetList = new ArrayList<>();
        int newTarBalance;
        int newReqBalance;

        // 거래정보 입력 루프

        do {
            // 사용자 입력 가져옴
            trade = PagePrinter.inputTradeInfo(selectNum);
            // 계좌 잔액조회
            con = driverConnector.connectDriver();
            try {
                if (trade.getAction() == "송금") {
                    requestList = accountDao.selectOne(con, trade.getRequestAccount());
                }
                targetList = accountDao.selectOne(con, trade.getTargetAccount());
            } catch (SQLException e) {}

            // 계좌번호 검증
            if (targetList.isEmpty()) {
                System.out.println("[Error] 올바른 계좌번호를 입력하세요.");
                continue;
            } else if (trade.getAction() == "송금" && targetList.isEmpty()) {
                System.out.println("[Error] 올바른 출금 계좌번호를 입력하세요.");
                continue;
            } else if (trade.getAction() == "송금" && requestList.isEmpty()) {
                System.out.println("[Error] 올바른 송금 계좌번호를 입력하세요.");
                continue;
            }

            // 거래 후 잔고 설정 및 유효성 검증
            if (trade.getAction() == "입금") {
                newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();
                trade.setTarBalance(newTarBalance);
            } else if (trade.getAction() == "출금"){
                newTarBalance = targetList.getFirst().getBalance() - trade.getAmount();
                if (newTarBalance < 0) {
                    System.out.println("출금액이 보유잔고를 초과합니다.");
                    continue;
                }
                trade.setTarBalance(newTarBalance);
            } else if (trade.getAction() == "송금"){
                newReqBalance = requestList.getFirst().getBalance() - trade.getAmount();
                newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();

                if (newReqBalance < 0) {
                    System.out.println("송금액이 보유잔고를 초과합니다.");
                    continue;
                }
                trade.setTarBalance(newTarBalance);
            }

            infoError = false;
        } while (infoError == true);

        // SQL 실행
        String resultMessage;
        con = driverConnector.connectDriver();
        try {
            resultMessage = dao.insert(con, trade);
            System.out.println(resultMessage);
            // 요청자 계좌 업데이트
            resultMessage = accountDao.updateOne(con,trade,true);
            // 수령자 계좌 업데이트
            resultMessage = accountDao.updateOne(con,trade,false);
            return resultMessage;
        } finally {
            if(con!=null) con.close();
        }
    }

    public String selectAccountHistory() throws SQLException {
        Connection con = null;
        Trade trade = new Trade();

        String user_id = sc.next();


        String resultMessage = "";

        return resultMessage;
    }

}
