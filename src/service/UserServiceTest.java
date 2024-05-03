package src.service;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.db.JdbcConnection;
import src.domain.User;
import src.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static src.Exception.GlobalException.log;

class UserServiceTest {

    @Test
    @DisplayName("회원 아이디 생성 및 조회")
    void findUser() {
        //given
        User user = new User("han","pass");
        boolean result = false;

//        UserService.createUser(user.getUserId(), user.getPassword());

        //when

        result = UserService.findUser(user.getUserId());

        //then
        assertTrue(result);
    }

}