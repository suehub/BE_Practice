package src.repository;

public class UserRepository {
    public static String createUser(){
        return "INSERT INTO User (UserId, password) VALUES (?, ?)";
    }

    public static String findUserId(){
        return "SELECT UserId FROM User WHERE Userid = ?";
    }
    public static String findUserPassword(){
        return "SELECT password FROM User WHERE password = ?";
    }
}
