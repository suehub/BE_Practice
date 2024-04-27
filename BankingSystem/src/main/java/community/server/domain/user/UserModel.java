package community.server.domain.user;


import community.server.db.ConnectionFactory;
import community.server.domain.user.banker.Banker;
import community.server.domain.user.customer.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserModel {

  private final User user;

  public UserModel(User user) {
    this.user = user;
  }

  void loginLogic(User user) {
    String username = user.name();
    String password = user.password();
    System.out.println("Login Logic");
    System.out.println("-----------------");
    ResultSet rs;
    String query = "SELECT * FROM user WHERE username = ?";
    try (Connection conn = ConnectionFactory.INSTANCE.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, username);
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getString("password").equals(password)) {
          System.out.println("Login successful");
        } else {
          System.out.println("Login failed");
        }
      }


    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  void registerLogic(User user) {

  }

  void logoutLogic(User user) {

  }

  void findUserLogic(User user) {

  }


  public void customerLogic(Customer customer) {
    Scanner sc = new Scanner(System.in);
    int choice = sc.nextInt();
    System.out.println("Customer Logic");
    System.out.println("-----------------");
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.println("3. Logout");
    switch (choice) {
      case 1:
        loginLogic(customer);
        break;
      case 2:
        registerLogic(customer);
        break;
      case 3:
        logoutLogic(customer);
        break;
      case 4:
        findUserLogic(customer);
        break;
    }

  }

  public void bankerLogic(Banker banker) {
    Scanner sc = new Scanner(System.in);
    int choice = sc.nextInt();
    System.out.println("Banker Logic");
    System.out.println("-----------------");
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.println("3. Logout");
    System.out.println("4. AdminMenu");
    switch (choice) {
      case 1:
        loginLogic(banker);
        break;
      case 2:
        registerLogic(banker);
        break;
      case 3:
        logoutLogic(banker);
        break;
      case 4:
        AdminMenu();
        break;
    }
  }

  private void AdminMenu() {

  }

  public void method() {
  }
}
