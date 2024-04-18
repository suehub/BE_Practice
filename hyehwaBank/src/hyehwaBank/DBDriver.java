package hyehwaBank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDriver {
	String dbDriver = "com.mysql.cj.jdbc.Driver";
	String dbUrl = "jdbc:mysql://@localhost:413/hyehwa_branch";
	String dbId = "root";
	String dbPw = "00000000";
	
	Connection conn = null;
	ResultSet result = null;
	
	public DBDriver() {
		
	}
	
	public void connectDB() {
		//db 연동
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbId, dbPw);
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		}catch (SQLException e) {
			System.out.println("에러 "+ e);
		}
	}
	
	public void insertData(String sql) {
		PreparedStatement pstmt = null;
		/*
		 * 찾아보기
		 */
		try {
			pstmt = conn.prepareStatement(sql);
			int count = pstmt.executeUpdate();
			
			if (count == 0) {
				System.out.println("다시 시도해 주세요.");
			}
		}catch (Exception e) {
			System.out.println("에러 "+e);
		}
		
	}
	
	public void updateData(String sql) {
		PreparedStatement pst = null;
		int rs;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getDataResult(String sql){
		String strAnswer = "";
		
		ResultSet result = getData(sql);
		try {
			while(result.next()) {
				strAnswer = result.getString(1);
			}
		}catch (SQLException e) {
			System.out.println("에러 "+e);
		}
		return strAnswer;
		
	}
	
//	public int getDataResult(String sql, int i) {
//		int intAnswer = -1;
//		
//		ResultSet result = getData(sql);
//		try {
//			while(result.next()) {
//				intAnswer = result.getInt(1);
//			}
//		}catch (SQLException e) {
//			System.out.println("에러 "+e);
//		}
//		
//		return intAnswer;
//	}
	
	
	public ResultSet getData(String sql) {
		Statement stmt = null;
		/*
		 * 어떻게 값을 가져올지 고민해보기
		 */
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
		}catch (Exception e) {
			System.out.println("에러 "+e);
		}
		return result;
		
	}
	
	public int CheckExist(String sql) {
		ResultSet result = null;
		int rs = -1;
		
		result = getData(sql);
		try {
			while(result.next()) {
				rs = result.getInt(1);
			}
		}catch (SQLException e) {
			System.out.println("에러 "+e);
		}
		
		return rs;
	}
	
	public void DBclose() {
		try {
			conn.close();
		}catch (SQLException e) {
			System.out.println("에러 "+e);
		}
		
	}
}
