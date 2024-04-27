package community.server.domain.user;

import com.fasterxml.uuid.Generators;
import java.nio.ByteBuffer;
import lombok.Getter;

@Getter
public class UUIDGenerator {
  private byte[] uuid;
  public UUIDGenerator(){
    this.uuid = new byte[16];
  }
  public void createUUID(){
    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
    byteBuffer.putLong(Generators.timeBasedGenerator().generate().getMostSignificantBits());
    byteBuffer.putLong(Generators.timeBasedGenerator().generate().getLeastSignificantBits());
    this.uuid = byteBuffer.array();
  }

}
