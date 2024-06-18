package com.example.yoonnsshop.items;

import com.example.yoonnsshop.domain.items.entity.Item;
import com.example.yoonnsshop.domain.items.ItemService;
import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ItemService itemService;

    @MockBean
    ItemRepository itemRepository;

    private final String baseUrl = "/api/v1/items";

    @Test
    @DisplayName("아이템 리스트 조회")
    public void getAllItems() throws Exception{
        // given
        Item item1 = Item.Builder.anItem()
                .withSeq(1L)
                .withName("item1")
                .withPrice(BigDecimal.valueOf(1000))
                .withDescription("item1 description")
                .build();

        Item item2 = Item.Builder.anItem()
                .withSeq(2L)
                .withName("item2")
                .withPrice(BigDecimal.valueOf(2000))
                .withDescription("item2 description")
                .build();

        Item item3 = Item.Builder.anItem()
                .withSeq(3L)
                .withName("item3")
                .withPrice(BigDecimal.valueOf(3000))
                .withDescription("item3 description")
                .build();

        // when
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> itemPage = new PageImpl<>(Arrays.asList(item1, item2, item3), pageable, 3);

        when(itemService.findAll(pageable)).thenReturn(itemPage);

        // then
        this.mockMvc.perform(get(baseUrl + "/v2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", is(3)));
    }

    @Test
    @DisplayName("아이템 Id로 조회")
    public void getItemById() throws Exception {
        // given
        Item item = Item.Builder.anItem()
                .withSeq(1L)
                .withName("item1")
                .withPrice(BigDecimal.valueOf(1000))
                .withDescription("item1 description")
                .build();

        // when
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

        this.mockMvc.perform(get(baseUrl + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(item.getName())));
    }
}
