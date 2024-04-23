package trade;


import repository.Query;

import java.sql.*;
import java.util.ArrayList;

public class TradeDao {

    public String insert(Connection con, Trade trade) throws SQLException {
        boolean result;
        String resultMessage = "";
        PreparedStatement pstmt = null;

        try {
            String sql = Query.TRADE_INSERT.getQueryString();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, trade.getAction());
            pstmt.setString(2, trade.getRequestAccount());
            pstmt.setString(3, trade.getTargetAccount());
            pstmt.setInt(4, trade.getAmount());
            pstmt.setInt(5, trade.getReqBalance());
            pstmt.setInt(6, trade.getTarBalance());
            result = pstmt.execute(); // 성공시 false, 실패시 true
            if (!result) {
                resultMessage = "[info] " + trade.getAction() + "을 진행합니다.";
            } else {
                resultMessage = "[Error] 거래에 실패하였습니다.";
            }
        } finally {
            if(pstmt!=null) pstmt.close();
        }

        return resultMessage;
    }

    public ArrayList<Trade> selectMutiple(Connection con, String userId, String accountNumber) throws SQLException {
        ArrayList<Trade> tradeList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = Query.TRADE_SELECT_MUTIPLE.getQueryString();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, accountNumber);
            rs = pstmt.executeQuery();
            int newSeq = 1;
            while (rs.next()) {
                int tradeId = newSeq;
                String action = rs.getString(2);
                String requestAccount = rs.getString(3);
                String targetAccount = rs.getString(4);
                int amount = rs.getInt(5);
                tradeList.add(new Trade(tradeId, action, requestAccount, targetAccount, amount));
                newSeq += 1;
            }
        } finally {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }

        return tradeList;
    }

}
