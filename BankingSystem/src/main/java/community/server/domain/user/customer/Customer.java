package community.server.domain.user.customer;

import community.server.domain.user.User;

public record Customer(byte[] customer_uuid,String customer_name, String customer_password) implements User {

  @Override
  public String name() {
    return customer_name;
  }

  @Override
  public String password() {
    return customer_password;
  }

  @Override
  public void run() {

  }

}
