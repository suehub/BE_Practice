package community.server.domain.user;


import static org.mockito.ArgumentMatchers.anyString;

import community.server.db.ConnectionFactory;
import community.server.security.PasswordEncryption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;


class MockUserModelTest {
  private User mockUser;

  @InjectMocks
  private UUIDGenerator uuidGenerator;

  @Getter
  @Setter
  static class User {
    String name;
    String password;
    byte[] passwordBytes;
    byte[] uuidBytes;
    public User(String name, String password){
      this.name = name;
      this.password = password;
    }
  }

  @BeforeEach
  void setUp() {
    this.mockUser = new User("test", "verifiedPassword");
  }


  @Test
  @DisplayName("Banker Register Logic Mock")
  void bankerRegisterLogicTestMock() throws SQLException {

    Connection mockConn = Mockito.mock(Connection.class);
    PreparedStatement mockPstmt = Mockito.mock(PreparedStatement.class);
    Mockito.when(mockConn.prepareStatement(anyString())).thenReturn(mockPstmt);
    Mockito.when(mockPstmt.executeUpdate()).thenReturn(1);

    // 테스트 실행
    BankerRegistrationService bankerRegistrationService = new BankerRegistrationService(mockConn);
    bankerRegistrationService.register((community.server.domain.user.User) mockUser);

    // 검증 , 랜덤 바이트 추가는 모킹이 불가능
    Mockito.verify(mockPstmt).setBytes(1, uuidGenerator.getUuid());
    Mockito.verify(mockPstmt).setString(2, mockUser.name);
    Mockito.verify(mockPstmt).setBytes(3, PasswordEncryption.hashPassword(mockUser.password));
    Mockito.verify(mockPstmt).setBoolean(4, true);
    Mockito.verify(mockPstmt).setNull(5, Types.TIMESTAMP);
    Mockito.verify(mockPstmt).executeUpdate();
  }


  @Test
  public void loginLogic() {
    String username = mockUser.getName();
    String password = mockUser.getPassword();
    System.out.println("Login Logic");
    System.out.println("-----------------");
    ResultSet rs;
    String query = "SELECT * FROM banker WHERE banker_name = ?";
    try(Connection conn = ConnectionFactory.INSTANCE.getConnection()){
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, username);
      rs = ps.executeQuery();
      if(rs.next()){
        byte[] returnPassword = rs.getBytes("password");
        if(PasswordEncryption.verifyPassword(returnPassword, password)){
          System.out.println("Login successful");
        }else{
          System.out.println("Login failed");
        }
      }


    }catch (Exception e){
      e.printStackTrace();
    }

  }
  @AfterEach
  void tearDown() {
    String name = mockUser.getName();
    try(Connection conn = ConnectionFactory.INSTANCE.getConnection()) {
      PreparedStatement pstmt = conn.prepareStatement("delete from banker where banker_name = ?");
      pstmt.setString(1, name);
      int result = pstmt.executeUpdate();
      if (result == 1) {
        System.out.println("delete success");
      }
      conn.commit();
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

}