package community.server.domain.user;

import community.server.domain.user.MockUserModelTest.MockUser;
import community.server.security.PasswordEncryption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BankerRegistrationServiceTest {
  private final Connection conn;

  public BankerRegistrationServiceTest(Connection mockConn) {
    this.conn = mockConn;
  }

  public void register(MockUser mockUser) throws SQLException {
    String sql = "INSERT INTO banker(banker_uuid, banker_name, banker_password, banker_admin_auth, banker_deleted_at) VALUES(?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(sql);
    // UUID 및 기타 필드 설정
    UUIDGeneratorTest uuidGenerator = new UUIDGeneratorTest();
    byte[] hashedPassword = PasswordEncryption.hashPassword(mockUser.password);
    uuidGenerator.createUUID();
    byte[] uuid = uuidGenerator.getUuid();
    pstmt.setBytes(1, uuid); // uuidBytes는 외부에서 생성된 UUID 바이트 배열
    pstmt.setString(2, mockUser.name);
    pstmt.setBytes(3, hashedPassword); // password는 해시된 비밀번호 바이트 배열
    pstmt.setBoolean(4, false);
    pstmt.setNull(5, Types.TIMESTAMP);
    pstmt.executeUpdate();
  }

}
