package community.server.domain.user.mapper;

import community.server.domain.user.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
  Customer userDtoToUserModel(Customer customer);

}
