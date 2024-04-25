package community.server.feature;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.*;
class AccountDepositTest {
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


  @Test
  @DisplayName("Test Account Exists")
  void testAccountExists() {
    // 가짜 입력을 사용하여 계정이 존재하는 경우를 테스트합니다.
    String input = "14"; // 존재하는 계정 번호
    byte[] inputBytes = input.getBytes(); // 입력 문자열을 바이트 배열로 변환

    // 바이트 배열을 입력 소스로 사용하는 ByteArrayInputStream 생성
    ByteArrayInputStream in = new ByteArrayInputStream(inputBytes);
    Scanner fakeScanner = new Scanner(in);

    System.setOut(new PrintStream(outputStreamCaptor));

    AccountDeposit accountCheck = new AccountDeposit(fakeScanner);
    accountCheck.accountCheck();

    // 테스트 결과를 출력한 후, 해당 결과를 String으로 저장하여 확인합니다.
    String printedOutput = getPrintedOutput();
    Assertions.assertThat(printedOutput).contains("Account Number: 14");
  }
  @Test
  @DisplayName("If deposit amount is negative or zero")
  void testDepositAmount(){
    String input = "14";
    byte[] inputBytes = input.getBytes();
    ByteArrayInputStream in = new ByteArrayInputStream(inputBytes);
    Scanner fakeScanner = new Scanner(in);
    System.setOut(new PrintStream(outputStreamCaptor));
    AccountDeposit accountCheck = new AccountDeposit(fakeScanner);

  }

  @Test
  @DisplayName("Test Account Not Exists")
  void testAccountNotExists() {
    // 가짜 입력을 사용하여 계정이 존재하지 않는 경우를 테스트합니다.
    String input = "99999"; // 존재하지 않는 계정 번호
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Scanner fakeScanner = new Scanner(in);
    System.setOut(new PrintStream(outputStreamCaptor));

    AccountDeposit accountCheck = new AccountDeposit(fakeScanner);
    accountCheck.accountCheck();

    // 테스트 결과를 출력한 후, 해당 결과를 String으로 저장하여 확인합니다.
    String printedOutput = getPrintedOutput();
    Assertions.assertThat(printedOutput).contains("Account not found");
  }

  private String getPrintedOutput() {
    // 표준 출력에 출력된 내용을 문자열로 반환합니다.
    return outputStreamCaptor.toString().trim();
  }

}