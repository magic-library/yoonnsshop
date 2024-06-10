package com.example.yoonnsshop.admins;

import com.example.yoonnsshop.domain.admins.AdminService;
import com.example.yoonnsshop.domain.admins.dto.LoginDto;
import com.example.yoonnsshop.domain.admins.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AdminService adminService;

    @MockBean
    AdminRepository adminRepository;

    private final String baseUrl = "/api/v1/admins";
    @Test
    public void authenticate() throws Exception {
        // given
        LoginDto loginDto = new LoginDto();
        loginDto.setPrincipal("test@test.com");
        loginDto.setCredentials("test123");

        String expectedToken = "expectedToken";


        given(adminService.authenticate(any(LoginDto.class))).willReturn(expectedToken);

        // when
        mockMvc.perform(post(baseUrl + "/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.data").value(expectedToken));
    }
}
