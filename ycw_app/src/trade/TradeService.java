package trade;

import account.Account;
import account.AccountDao;
import client.Status;
import controller.Message;
import controller.Tag;
import repository.DriverConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TradeService {

    DriverConnector driverConnector = new DriverConnector();
    AccountDao accountDao = new AccountDao();
    TradeDao dao = new TradeDao();

    public Status insert(Status status, Trade trade) throws SQLException {
        Connection con = null;
        ArrayList<Account> targetList;

        // 신규 입/출금 Trade객체 유효성 검증
        con = driverConnector.connectDriver();
        try {
            targetList = accountDao.selectOne(con, trade.getTargetAccount());
        } finally {
            if(con!=null) con.close();
        }

        if (targetList.isEmpty()) {
            status.setMessage(Message.ERROR_WRONG_ACCOUNT.getMessage());
            return status;
        }

        int newTarBalance = 0;
        if (trade.getAction().equals(Tag.ACTION_DEPOSIT)) {
            newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();
        } else if (trade.getAction().equals(Tag.ACTION_WITHDRAW)){
            newTarBalance = targetList.getFirst().getBalance() - trade.getAmount();
        }

        if (trade.getAction().equals(Tag.ACTION_WITHDRAW) && newTarBalance < 0) {
            status.setMessage(Message.ERROR_FAILED_TRANSFER_OVER_BALANCE.getMessage(Tag.ACTION_WITHDRAW.getTag()));
            return status;
        }
        trade.setTarBalance(newTarBalance);

        // 신규 입/출금 Trade insert 후, 거래 결과 account에 update
        String resultMessage;
        con = driverConnector.connectDriver();
        try {
            resultMessage = dao.insert(con, trade);
            if (resultMessage.contains(Message.ERROR.getMessage())){
                status.setMessage(resultMessage);
                return status;
            }
            System.out.println(resultMessage);
            status.setMessage(accountDao.updateOne(con,trade,false));
        } finally {
            if(con!=null) con.close();
        }
        status.setWorkTag(Tag.MAIN);

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
            status.setMessage(Message.ERROR_WRONG_REQUEST_ACCOUNT.getMessage());
            return status;
        } else if (requestList.isEmpty()) {
            status.setMessage(Message.ERROR_WRONG_TARGET_ACCOUNT.getMessage());
            return status;
        }

        int newReqBalance = requestList.getFirst().getBalance() - trade.getAmount();
        int newTarBalance = targetList.getFirst().getBalance() + trade.getAmount();

        if (newReqBalance < 0) {
            status.setMessage(Message.ERROR_FAILED_TRANSFER_OVER_BALANCE.getMessage(Tag.ACTION_TRANSFER.getTag()));
            return status;
        }
        trade.setTarBalance(newTarBalance);
        trade.setReqBalance(newReqBalance);

        // 신규 송금 Trade insert 후 각 account에 송금결과 update
        con = driverConnector.connectDriver();
        try {
            status.setMessage(dao.insert(con, trade));
            // 요청 계좌 업데이트
            status.setMessage(accountDao.updateOne(con,trade,true));
            // 수령 계좌 업데이트
            status.setMessage(accountDao.updateOne(con,trade,false));
            status.setWorkTag(Tag.MANAGE_ACCOUNTS);
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
            tradeList = dao.selectMutiple(con, status.getUserId(), status.getData()); // accountNumber 주입
        } finally {
            if(con!=null) con.close();
        }

        return tradeList;
    }

}
