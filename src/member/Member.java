package member;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public class Member {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static Map<String, String> env = getenv();
    private static final String URL = "jdbc:mysql://localhost:3306/javabank";
    private static final String USERNAME = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    // 로그인 기능
    public int login(String id, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE user_id=? AND user_password=?")) {
            statement.setString(1, id);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id = resultSet.getString("user_id");
                this.name = resultSet.getString("user_name");

                return 1;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 회원가입 기능
    public void signup(String name, String id, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Users(user_id, user_password, user_name) VALUES(?, ?, ?)")) {
            statement.setString(1, id);
            statement.setString(2, password);
            statement.setString(3, name);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("회원가입 되었습니다.");
            } else {
                System.out.println("회원가입에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
