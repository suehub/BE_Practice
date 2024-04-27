package community.server.domain.user.mapper;

import static org.junit.jupiter.api.Assertions.*;

import community.server.domain.user.banker.Banker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankerMapperTest {
  private final BankerMapper bankerMapper = BankerMapper.INSTANCE;
  @Test
  @DisplayName("매퍼가 모델을 전달해주는지 확인")
  void testBankerDtoToUserModel() {
    // given
    byte[] testUUID = new byte[16];
    String samplePassword = "password";
    Banker banker = new Banker(testUUID, "password", samplePassword, true);
    //when
    Banker bankerModel = bankerMapper.userDtoToUserModel(banker);
    //then
    assertEquals(banker.name(), bankerModel.name());
  }
}