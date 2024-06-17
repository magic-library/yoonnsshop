package com.example.yoonnsshop.carts;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockHttpSession session;

    @BeforeEach
    void setup() {
        session = new MockHttpSession();
    }

    private final String baseUrl = "/api/v1/carts";

    @Test
    @Description("상품을 장바구니에 추가")
    public void addItemToCart() throws Exception {
        // when
        mockMvc.perform(post(baseUrl + "/{itemId}", 1L)
                        .session(session)
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        // then
        assertEquals(2, cart.get(1L));
    }

    @Test
    @Description("장바구니 조회")
    public void getCart() throws Exception {
        // given
        session.setAttribute("cart", Map.of(1L, 2));

        // when/then
        mockMvc.perform(get(baseUrl)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Description("장바구니에 담긴 상품 수량 수정")
    public void updateItemInCart() throws Exception {
        // given
        Map<Long, Integer> cart = new HashMap<>();
        cart.put(1L, 2);
        session.setAttribute("cart", cart);

        // when
        mockMvc.perform(put(baseUrl + "/{itemId}", 1L)
                        .session(session)
                        .param("quantity", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        assertEquals(3, cart.get(1L));
    }

    @Test
    @Description("장바구니에서 상품 삭제")
    public void removeItemFromCart() throws Exception {
        // given
        Map<Long, Integer> cart = new HashMap<>();
        cart.put(1L, 2);
        session.setAttribute("cart", cart);

        // when
        mockMvc.perform(delete(baseUrl + "/{itemId}", 1L)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        assertEquals(0, cart.size());
    }
}
