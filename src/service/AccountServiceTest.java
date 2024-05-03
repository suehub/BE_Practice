package src.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class AccountServiceTest {


    @Test
    @DisplayName("입금 테스트")
    void testDeposit() {
        // given
        String accountId = "65249";
        int balance = AccountService.balance(accountId);
        int amount = 10000;

        // when
        AccountService.deposits(accountId, amount);

        // then
        int finalBalance = AccountService.balance(accountId);
        assertEquals(balance + amount, finalBalance); // 입금 후 잔액이 증가했는지 확인
    }

    @Test
    @DisplayName("출금 테스트")
    void testWithdraw() {
        // given
        String accountId = "65249";
        int balance = AccountService.balance(accountId);
        int amount = 1000;

        // when
        boolean result = AccountService.withdraw(amount, accountId);

        // then
        assertTrue(result);
        assertEquals(balance - amount, AccountService.balance(accountId));
    }
}