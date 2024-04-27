package community.server.security;

import static org.junit.jupiter.api.Assertions.*;

import community.server.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordEncryptionTest {

  static class User{
    String name;
    String password;
    public User(String name, String password){
      this.name = name;
      this.password = password;
    }
  }
  @Test
  @DisplayName("비밀번호 검증 테스트")
  void sameUserVerification() {
    //given
    User user = new User("test", "test");
    //when
    byte[] password = PasswordEncryption.hashPassword(user.password);
    //then
    assertTrue(PasswordEncryption.verifyPassword(password, user.password));
  }
  @Test
  @DisplayName("비밀번호 실패 테스트")
  void sameUserFailedVerification(){
    //gien
    User user = new User("test", "test");
    //when
    byte[] password = PasswordEncryption.hashPassword(user.password);
    //then
    assertFalse(PasswordEncryption.verifyPassword(password, "test2"));
  }
  @Test
  @DisplayName("DB 비밀번호 검증테스트")
  void sameUserDBPasswordVerification(){
    //given
    User user = new User("test", "test");
    //when
    byte[] password = PasswordEncryption.hashPassword(user.password);
    try(Connection conn = ConnectionFactory.INSTANCE.getConnection()){
      PreparedStatement pstmt = conn.prepareStatement("insert into customer(customer_name, customer_password) values('test',?)");
      pstmt.setBytes(1, password);
      int result =pstmt.executeUpdate();
      if(result ==1){
        System.out.println("insert success");
      }
      conn.commit();
    }catch(Exception e){
      e.printStackTrace();
    }
    //then
    try(Connection conn = ConnectionFactory.INSTANCE.getConnection()) {
      PreparedStatement pstmt = conn.prepareStatement(
          "select customer_password from customer where customer_name = 'test'");
      ResultSet rs = pstmt.executeQuery();
      conn.commit();
      if(rs.next()){
        byte[] dbPasswordBytes = rs.getBytes("customer_password");
        assertTrue(PasswordEncryption.verifyPassword(dbPasswordBytes, user.password));
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }


}