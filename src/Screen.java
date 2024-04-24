package src;

public class Screen {

    public void menuId(){
        System.out.println("--------------------------");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("0. 종료");
    }

    public void menu(){
        System.out.println("--------------------------");
        System.out.println("원하는 작업을 선택하세요:");
        System.out.println("1. 계좌 관련 기능");
        System.out.println("2. 입출금 기능");
        System.out.println("3. 로그 아웃");
        System.out.println("0. 종료");

    }
    public void login(){
        System.out.println("--------------------------");
        System.out.println("원하는 작업을 선택하세요:");
        System.out.println("1. 계좌 생성");
        System.out.println("2. 계좌 조회");
        System.out.println("3. 홈으로 돌아가기");
    }


    public void transaction() {
        System.out.println("--------------------------");
        System.out.println("원하는 작업을 선택하세요:");
        System.out.println("1. 입금");
        System.out.println("2. 출금");
        System.out.println("3. 송금");
        System.out.println("4. 홈으로 돌아가기");

    }
}
