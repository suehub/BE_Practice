package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnector {
    private String driver;
    private String url;
    private String database;
    private String userName;
    private String password;

    public DriverConnector() {
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.url = "bepractice-db.cnyc406m4kh6.ap-northeast-2.rds.amazonaws.com";
        this.database = "practice_db";
        this.userName = "admin";
        this.password = "Ucheol92!4";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Connection connectDriver() throws SQLException {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:mysql://" + url + "/" + database, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return con;
    }
}
