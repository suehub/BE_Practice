package community.server.domain.user.customer;

import community.server.domain.user.User;

public record Customer(byte[] customer_uuid,String customer_name, String password) implements User {

  @Override
  public String name() {
    return customer_name;
  }
  @Override
  public void run() {

  }

}
