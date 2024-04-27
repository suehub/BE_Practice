package community.Client;

import community.server.domain.user.banker.Banker;
import community.server.domain.user.customer.Customer;
import community.server.domain.user.User;
import community.server.domain.user.UserModel;
import community.server.domain.user.UserService;
import community.server.domain.user.mapper.BankerMapper;
import community.server.domain.user.mapper.CustomerMapper;
import java.util.Scanner;

public class UserView {
  private final CustomerMapper customerMapper;
  private final BankerMapper bankerMapper;
  private final UserService userService;
  public UserView(CustomerMapper userMapper, BankerMapper bankerMapper, UserModel userModel,
      UserService userService, User user, Banker banker, Customer customer){
    this.customerMapper = userMapper;
    this.bankerMapper = bankerMapper;
    this.userService = userService;
  }

  public void userMenu(){
    System.out.println("1. Customer");
    System.out.println("2. Banker");
  }
  public void userInput(){
    Scanner sc = new Scanner(System.in);

  }
  public void chooseMenu(int choice){
    switch(choice) {
      case 1:
        Customer customer = new Customer("customer", "password", "customer");
        userService.processCustomer(customerMapper.userDtoToUserModel(customer));
        break;
      case 2:
        Banker banker = new Banker("banker", "password", "wowwow");
        userService.processBanker(bankerMapper.userDtoToUserModel(banker));
        break;
    }
  }



}
