package community.server.domain.user.mapper;

import static org.junit.jupiter.api.Assertions.*;

import community.server.domain.user.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {
  private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

  @Test
  @DisplayName("고객매퍼가 모델에 전달해주는지 확인")
  void testCustoemrDtoToUserModel() {
    // given
    byte[] testUUID = new byte[16];
    String samplePassword = "password";
    Customer customer = new Customer(testUUID, "password", samplePassword);
    //when
    Customer customerModel = customerMapper.userDtoToUserModel(customer);
    //then
    assertEquals(customer.name(), customerModel.name());
  }

}