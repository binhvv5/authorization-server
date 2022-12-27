package com.minde.authorizationserver;

import com.minde.authorizationserver.dtoes.auth.UserDTO;
import com.minde.authorizationserver.mapper.TbdUserMapper;
import com.minde.authorizationserver.models.auth.TbdUser;
import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthorizationServerApplicationTest {

    @Autowired
    private TbdUserMapper tbdUserMapper;

    @Test
    void testMappingUser(){
//        TbdUser tbdUser = new TbdUser();
//        tbdUser.setUsername("binhvv");
//        tbdUser.setPassword("12345");
//
//        UserDTO userDTO = tbdUserMapper.toDto(tbdUser);
//        assert userDTO.getUsername()!=null;
    }
}
