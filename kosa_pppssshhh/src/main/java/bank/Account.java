package bank;

//데이터 관리 클래스
class Account {

    //인스턴스 변수
    private String accountNumber; //계좌번호
    private String name; //이름
    private String pwd;  //비밀번호
    private int balance; //잔고

    //디폴트 생성자 메소드
    public Account() {

    }

    //매개변수 생성자 메소드
    public Account(String accountNumber, String name, String pwd, int balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.pwd = pwd;
        this.balance = balance;
    }

    //전체 계좌를 조회하다.
    public void printAccounts() {
        System.out.printf("계좌번호 : %s, 이름 : %s, 비밀번호 : %s, 잔고 : %d원%n", accountNumber, name, pwd, balance);
    }

    //입금하다.
    public void deposit(int amount) {
        this.balance += amount;
    }

    //출금하다.
    public void withdraw(int amount) {
        this.balance -= amount;
    }

    //잔고 get
    public int getBalance() {
        return this.balance;
    }

    // 계좌번호 get
    public String getAccountNumber() {
        return this.accountNumber;
    }

}

