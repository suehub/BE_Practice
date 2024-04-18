import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

import user.User;
import user.UserService;

public class bankingApp {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        do {
            System.out.println("  뱅킹 시스템");
            System.out.println("----------------------------------");
            System.out.println("  1. 사용자 생성");
            System.out.println("  2. 사용자 조회");
            System.out.println("  3. 계좌 생성");
            System.out.println("  4. 계죄 조회");
            System.out.println("  5. 계좌 삭제");
            System.out.println("  6. 입금");
            System.out.println("  7. 출금");
            System.out.println("  8. 송금");
            System.out.print("입력 : ");
            int selectNum = sc.nextInt();
            if (selectNum < 0 && selectNum > 8) {
                System.console().flush();
                System.out.println("1. ~ 8.  사이로 입력해주세요");
                continue;
            }
            System.out.flush();
            switch (selectNum) {
                case 1, 2 -> userMenu(selectNum);
            }
        } while (true);
    }



    public static void userMenu (int selectNum){
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
            default -> throw new IllegalStateException("잘못된 입력 입니다.");
        }
    }
}
