package src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    public static Connection JdbcConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjdbc", "root", "qwer1234");

            if(conn == null){
                new Exception("connection 실패");
                System.exit(-1);
            }
            return conn;

        } catch(SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        }
        return null;
    }
}
