import account.Account;
import account.AccountService;
import user.User;
import user.UserService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Service {

    void serviceMenu (int selectNum){
        switch (selectNum) {
            case 1, 2 -> userService(selectNum);
            case 3, 4, 5  -> accountService(selectNum);
            case 6, 7, 8 -> tradeService(selectNum);
            default -> throw new IllegalStateException("잘못된 입력 입니다.");
        }
    }

    void userService (int selectNum){
        UserService service = new UserService();

        switch (selectNum){
            case 1 -> {
                String result = "";
                try {
                    result = service.insert();
                    System.out.println(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                ArrayList<User> list = new ArrayList<>();

                try {
                    list = service.selectOne();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for(User user: list) {
                    System.out.println("사용자 조회 결과");
                    System.out.println(user.toString());
                    System.out.println();
                }
            }
        }
    }

    void accountService(int selectNum) {
        AccountService service = new AccountService();

        switch (selectNum) {

            case 3 -> {
                String result = "";
                try {
                    result = service.insert();
                    System.out.println(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> {
                ArrayList<Account> list = new ArrayList<>();

                try {
                    list = service.selectOne();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Account account : list) {
                    System.out.println("사용자 조회 결과");
                    System.out.println(account.toString());
                    System.out.println();
                }
            }

            case 5 -> {
                String result = "";
                try {
                    result = service.delete();
                    System.out.println(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    void tradeService(int selectNum) {

    }


}
