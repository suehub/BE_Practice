package trade;

import account.Account;
import account.AccountDao;
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

    public Status insert(Status status, Trade trade) throws SQLException {
        Connection con = null;
        ArrayList<Account> targetList = new ArrayList<>();

        // 신규 입/출금 Trade객체 유효성 검증
        con = driverConnector.connectDriver();
        try {
            targetList = accountDao.selectOne(con, trade.getTargetAccount());
        } finally {
            if(con!=null) con.close();
        }

        if (targetList.isEmpty()) {
            status.setMessage("[Error] 올바른 계좌번호를 입력하세요.");
            return status;
        }

        int newTarBalance = 0;
        if (trade.getAction() == "입금") {
            newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();
        } else if (trade.getAction() == "출금"){
            newTarBalance = targetList.getFirst().getBalance() - trade.getAmount();
        }

        if (trade.getAction() == "출금" && newTarBalance < 0) {
            status.setMessage("[Error] 출금액이 보유잔고를 초과합니다.");
            return status;
        }
        trade.setTarBalance(newTarBalance);

        // 신규 입/출금 Trade insert 후, 거래 결과 account에 update
        String resultMessage;
        con = driverConnector.connectDriver();
        try {
            resultMessage = dao.insert(con, trade);
            if (resultMessage.contains("[Error]")){
                status.setMessage(resultMessage);
                return status;
            }
            System.out.println(resultMessage);
            status.setMessage(accountDao.updateOne(con,trade,false));
        } finally {
            if(con!=null) con.close();
        }
        status.setWorkName("main");
        status.setWorkFlow("redirect");

        return status;
    }

    public Status transfer(Status status, Trade trade) throws SQLException {
        Connection con = null;
        ArrayList<Account> requestList = new ArrayList<>();
        ArrayList<Account> targetList = new ArrayList<>();
        String resultMessage;

        // 신규 송금 Trade객체 유효성 검증ㅇ
        con = driverConnector.connectDriver();
        try {
            requestList = accountDao.selectOne(con, trade.getRequestAccount());
            targetList = accountDao.selectOne(con, trade.getTargetAccount());
        } finally {
            if(con!=null) con.close();
        }

        if (targetList.isEmpty()) {
            status.setMessage("[Error] 올바른 보낼 계좌번호를 입력하세요.");
            return status;
        } else if (requestList.isEmpty()) {
            status.setMessage("[Error] 올바른 받는 계좌번호를 입력하세요.");
            return status;
        }

        int newReqBalance = requestList.getFirst().getBalance() - trade.getAmount();
        int newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();

        if (newReqBalance < 0) {
            status.setMessage("[Error] 송금액이 보유잔고를 초과합니다.");
            return status;
        }
        trade.setTarBalance(newTarBalance);
        trade.setReqBalance(newReqBalance);

        // 신규 송금 Trade insert 후 각 account에 송금결과 update
        con = driverConnector.connectDriver();
        try {
            status.setMessage(dao.insert(con, trade));
            // 요청자 계좌 업데이트
            status.setMessage(accountDao.updateOne(con,trade,true));
            // 수령자 계좌 업데이트
            status.setMessage(accountDao.updateOne(con,trade,false));


            status.setWorkName("manage_account");
            status.setWorkFlow("redirect");
        } finally {
            if(con!=null) con.close();
        }
        return status;
    }

    public ArrayList<Trade> selectAccountHistory(Status status) throws SQLException {
        Connection con = null;
        ArrayList<Trade> tradeList;

        con = driverConnector.connectDriver();
        try{
            tradeList = dao.selectAccountHistory(con, status.getUserId(), status.getData()); // accountNumber 주입
        } finally {
            if(con!=null) con.close();
        }

        return tradeList;
    }

}
