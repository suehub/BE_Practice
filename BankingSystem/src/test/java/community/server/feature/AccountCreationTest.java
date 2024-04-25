package community.server.feature;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.uuid.Generators;
import java.nio.ByteBuffer;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccountCreationTest {
  @Test
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

}