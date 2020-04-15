package com.Bostic.BosticApp;

import com.Bostic.BosticApp.domains.JWTBlacklist;
import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JWTRepoIntegrationTest {

    @Autowired
    private static TestEntityManager testEntityManager;
     @Autowired
    private JWTBlacklistRepository jwtBlacklistRepository;

    @Test
    public void findAnyField(){
        JWTBlacklist jwtBlacklist = new JWTBlacklist("skdjfeifs564", new Date(System.currentTimeMillis()));
        jwtBlacklistRepository.save(jwtBlacklist);

        String signature =  jwtBlacklistRepository.findById("skdjfeifs564").get().getJWTsignature();

        assertThat(signature).isEqualTo("skdjfeifs564");
    }

}
