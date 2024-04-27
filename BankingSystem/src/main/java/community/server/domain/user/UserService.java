package community.server.domain.user;

import community.server.domain.user.banker.Banker;
import community.server.domain.user.customer.Customer;

public class UserService {
  private final UserModel userModel;

  public UserService(UserModel userModel) {
    this.userModel = userModel;
  }

  public void processBanker(Banker bankerDto){
   Banker banker = bankerConvertToEntity(bankerDto);
   userModel.bankerLogic(banker);
  }

  private Banker bankerConvertToEntity(Banker bankerDto) {
    return new Banker(bankerDto.name(), bankerDto.password(), bankerDto.authentication());
  }

  public void processCustomer(Customer customerDto){
    Customer customer = customerConvertToEntity(customerDto);
    userModel.customerLogic(customer);

  }

  private Customer customerConvertToEntity(Customer customerDto ) {
    return new Customer(customerDto.name(), customerDto.password(), customerDto.role());
  }

}
