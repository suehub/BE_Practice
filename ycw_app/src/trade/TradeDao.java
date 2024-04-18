package trade;

import java.sql.*;

public class TradeDao {

    public String insert(Connection con, Trade trade) throws SQLException {
        boolean result;
        String resultMessage = "";
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into Trades values(?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, trade.getTradeId());
            pstmt.setString(2, trade.getRequestAccount());
            pstmt.setString(3, trade.getTargetAccount());
            pstmt.setString(4, trade.getAction());
            pstmt.setInt(5, trade.getAmount());
            pstmt.setInt(6, trade.getBalance());
            result = pstmt.execute(); // 성공시 false, 실패시 true
            if (result) {
                resultMessage = "거래에 실패하였습니다.";
            }
        } finally {
            con.close();
            if(pstmt!=null)pstmt.close();
        }

        return resultMessage;
    }

}
