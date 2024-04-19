package trade;

import java.sql.*;

public class TradeDao {

    public String insert(Connection con, Trade trade) throws SQLException {
        boolean result;
        String resultMessage = "";
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into Trades values(?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trade.getTradeId());
            pstmt.setString(2, trade.getUserId());
            pstmt.setString(3, trade.getRequestAccount());
            pstmt.setString(4, trade.getAction());
            pstmt.setString(5, trade.getTargetAccount());
            pstmt.setInt(6, trade.getAmount());
            pstmt.setInt(7, trade.getReqBalance());
            pstmt.setInt(8, trade.getTarBalance());
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

}
