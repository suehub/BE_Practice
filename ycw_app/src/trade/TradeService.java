package trade;

import account.Account;
import account.AccountDao;
import page.PagePrinter;
import page.Status;
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

    public String insert(Trade trade) throws SQLException {
        Connection con = null;

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

    public Status transfer(Status status, Trade trade) throws SQLException {
        Connection con = null;
        String resultMessage;
        ArrayList<Account> requestList = new ArrayList<>();
        ArrayList<Account> targetList = new ArrayList<>();

        // 계좌 조회
        con = driverConnector.connectDriver();
        try {
            if (trade.getAction() == "송금") {
                requestList = accountDao.selectOne(con, trade.getRequestAccount());
            }
            targetList = accountDao.selectOne(con, trade.getTargetAccount());
        } catch (SQLException e) {}

        if (targetList.isEmpty() && (trade.getAction() == "입금" || trade.getAction() == "출금" )) {
            status.setMessage("[Error] 올바른 계좌번호를 입력하세요.");
            return status;
        } else if (trade.getAction() == "송금" && targetList.isEmpty()) {
            status.setMessage("[Error] 올바른 보낼 계좌번호를 입력하세요.");
            return status;
        } else if (trade.getAction() == "송금" && requestList.isEmpty()) {
            status.setMessage("[Error] 올바른 받는 계좌번호를 입력하세요.");
            return status;
        }

        // 거래 후 잔고 설정 및 유효성 검증
        int newTarBalance, newReqBalance;

        if (trade.getAction() == "입금") {
            newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();
            trade.setTarBalance(newTarBalance);
        } else if (trade.getAction() == "출금"){
            newTarBalance = targetList.getFirst().getBalance() - trade.getAmount();
            if (newTarBalance < 0) {
                status.setMessage("[Error] 출금액이 보유잔고를 초과합니다.");
                return status;
            }
            trade.setTarBalance(newTarBalance);
        } else if (trade.getAction() == "송금"){
            newReqBalance = requestList.getFirst().getBalance() - trade.getAmount();
            newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();

            if (newReqBalance < 0) {
                status.setMessage("[Error] 송금액이 보유잔고를 초과합니다.");
                return status;
            }
            trade.setTarBalance(newTarBalance);
        }

        // 송금결과 각 account에 update
        con = driverConnector.connectDriver();
        try {
            status.setMessage(dao.insert(con, trade));
            // 요청자 계좌 업데이트
            resultMessage = accountDao.updateOne(con,trade,true);
            // 수령자 계좌 업데이트
            resultMessage = accountDao.updateOne(con,trade,false);

        } finally {
            if(con!=null) con.close();
        }
        return status;
    }

    public String selectAccountHistory() throws SQLException {
        Connection con = null;
        Trade trade = new Trade();

        String user_id = sc.next();

        String resultMessage = "";

        return resultMessage;
    }

}
