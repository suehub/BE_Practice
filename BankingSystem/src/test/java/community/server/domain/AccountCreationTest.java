package community.server.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.uuid.Generators;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountCreationTest {
  @Test
  @DisplayName("Test if UUID is converted to binary")
  void createUUIDToBinary() {
    UUID uuid = Generators.timeBasedGenerator().generate();
    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
    byteBuffer.putLong(uuid.getMostSignificantBits());
    byteBuffer.putLong(uuid.getLeastSignificantBits());
    byte[] expected = byteBuffer.array();
    byte[] actual = uuidToBytes(uuid);
    assertArrayEquals(expected, actual);
  }
  private byte[] uuidToBytes(UUID uuid){
    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
    byteBuffer.putLong(uuid.getMostSignificantBits());
    byteBuffer.putLong(uuid.getLeastSignificantBits());
    return byteBuffer.array();

  }
  @Test
  @DisplayName("Test if account nickname is valid")
  public void isValidAccountNickname() {
    //given
    String input = "gangju_lee";
    //when
    String accountNicknamePattern = "^[\\w .'-]{1,20}$";
    //then
    assertTrue(input.matches(accountNicknamePattern));
  }
  @Test
  @DisplayName("Test if account nickname is invalid")
  public void isInvalidAccountNickname() {
    //given
    String input = "gangju_lee!";
    //when
    String accountNicknamePattern = "^[\\w .'-]{1,20}$";
    //then
    assertFalse(input.matches(accountNicknamePattern));
  }


}