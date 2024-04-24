package community.server.feature;

public class AccountSearch extends AccountCheck{

  public AccountSearch(){
    System.out.println("AccountSearch created!");
    System.out.println("pleas input the account number you want to search: ");
  }
  public void run(){
    accountCheck();
  }
}
