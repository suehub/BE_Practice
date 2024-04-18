package user;

public class User {
    private String userId;
    private String password;
    private String name;

    public User() {
        super();
    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
