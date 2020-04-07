package com.Bostic.BosticApp;

import com.Bostic.BosticApp.controller.LogoutController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LogoutController.class)
public class LogoutControllerTests {

    @Autowired
    private MockMvc mockMvc;

}
