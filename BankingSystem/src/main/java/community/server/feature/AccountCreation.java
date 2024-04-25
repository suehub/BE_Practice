package community.server.feature;

import com.fasterxml.uuid.Generators;
import community.server.db.ConnectionFactory;
import community.server.exceptions.GlobalExceptionConfig;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class AccountCreation  extends AccountCheck{
  private final Scanner sc;
  public AccountCreation(Scanner sc){
    this.sc = sc;
  }
  public void run(){
    AccountCreationDB();
  }

  private void AccountCreationDB() {
    System.out.println("what is your name? :");

    String accountNum = sc.nextLine();
    LocalDateTime accountOpen = LocalDateTime.now();
    int AccountType = 1;
    if(accountCheck()){
      System.out.println("Account already exists");
      return;
    }
    UUID uuid = Generators.timeBasedGenerator().generate();

    PreparedStatement ps;
    String sql = "insert into account (account_num, account_open, account_type, account_nickname) values (?,?,?,?)";
    try(Connection con = ConnectionFactory.INSTANCE.getConnection()){
      ps = Objects.requireNonNull(con).prepareStatement(sql);
      ps.setBytes(1, uuidToBytes(uuid));
      ps.setTimestamp(2, Timestamp.valueOf(accountOpen));
      ps.setInt(3, AccountType);
      ps.setString(4, accountNum);
      int result = ps.executeUpdate();
      if(result <= 0){
        con.rollback();
        System.out.println("Account failed");
      }
      con.commit();
    }catch(Exception e){
      GlobalExceptionConfig.log(e);
    }

  }
  private byte[] uuidToBytes(UUID uuid){
    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
    byteBuffer.putLong(uuid.getMostSignificantBits());
    byteBuffer.putLong(uuid.getLeastSignificantBits());
    return byteBuffer.array();

  }

}
