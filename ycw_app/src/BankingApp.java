import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("----------------------------------");
            System.out.println("             뱅킹 시스템            ");
            System.out.println("----------------------------------");
            System.out.println("  1. 사용자 생성");
            System.out.println("  2. 사용자 조회");
            System.out.println("  3. 계좌 생성");
            System.out.println("  4. 계좌 조회");
            System.out.println("  5. 계좌 삭제");
            System.out.println("  6. 입금");
            System.out.println("  7. 출금");
            System.out.println("  8. 송금");
            System.out.println("----------------------------------");
            System.out.println("  0. 종료");
            System.out.println("----------------------------------");
            System.out.print("입력 : ");
            int selectNum = sc.nextInt();
            if (selectNum < 0 && selectNum > 8) {
                System.console().flush();
                System.out.println("1. ~ 8.  사이로 입력해주세요");
                continue;
            }
            Service service = new Service();
            System.out.flush();
            service.serviceMenu(selectNum);
        } while (true);
    }

}
