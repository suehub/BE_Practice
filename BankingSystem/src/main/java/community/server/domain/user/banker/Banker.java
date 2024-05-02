package community.server.domain.user.banker;

import community.server.domain.user.User;

public record Banker(byte[] banker_uuid, String banker_name, String banker_password,
                     boolean authentication) implements User {

  @Override
  public String name() {
    return banker_name;
  }

  @Override
  public String password() {
    return banker_password;
  }

  @Override
  public void run() {

  }

  public void performAction() {

  }
}
