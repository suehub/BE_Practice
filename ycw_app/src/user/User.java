package user;

public class User {
    private int seqId;
    private String userId;
    private String password;
    private String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
        super();
    }

    public User(int seqId, String userId, String name) {
        this.seqId = seqId;
        this.userId = userId;
        this.name = name;
    }

    public int getseqId() {
        return seqId;
    }

    public void setseqId(int seqId) {
        this.seqId = seqId;
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
                "seqId=" + seqId +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
