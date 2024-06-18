package com.example.yoonnsshop.items;

import com.example.yoonnsshop.domain.items.ItemService;
import com.example.yoonnsshop.domain.items.dto.CreateItemRequestDto;
import com.example.yoonnsshop.domain.items.dto.UpdateItemRequestDto;
import com.example.yoonnsshop.domain.items.entity.Item;
import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import jdk.jfr.Description;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class AdminItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ItemService itemService;

    @MockBean
    ItemRepository itemRepository;

    private final String baseUrl = "/api/v1/admins/items";

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
//        Page<Item> itemPage = new PageImpl<>(Arrays.asList(item1, item2, item3), pageable, 3);

        when(itemRepository.getItemCount()).thenReturn(3L);
        when(itemRepository.findAllWithoutCounter(pageable)).thenReturn(Arrays.asList(item1, item2, item3));

        // then
        this.mockMvc.perform(get(baseUrl+ "/v2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalElements", is(3)));
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
                .andExpect(jsonPath("$.name", is(item.getName())));
    }

    @Test
    @Description("아이템 등록")
    public void registerItem() throws Exception {
        // given
        Item item = Item.Builder.anItem()
                .withSeq(1L)
                .withName("item1")
                .withPrice(BigDecimal.valueOf(1000))
                .withDescription("item1 description")
                .withStockQuantity(10)
                .build();

        CreateItemRequestDto createItemRequestDto = new CreateItemRequestDto();
        createItemRequestDto.setName(item.getName());
        createItemRequestDto.setPrice(item.getPrice());
        createItemRequestDto.setDescription(item.getDescription());
        createItemRequestDto.setStockQuantity(item.getStockQuantity());

        // when
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        this.mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createItemRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(item.getName())));
    }

    @Test
    @DisplayName("아이템 수정")
    public void updateItem() throws Exception {
        // given
        Item item = Item.Builder.anItem()
                .withSeq(1L)
                .withName("item1")
                .withPrice(BigDecimal.valueOf(1000))
                .withDescription("item1 description")
                .build();

        UpdateItemRequestDto updateRequest = new UpdateItemRequestDto();
        updateRequest.setName("Updated Item Name");
        updateRequest.setPrice(BigDecimal.valueOf(2000));
        updateRequest.setDescription("Updated description");
        updateRequest.setStockQuantity(10);

        Item updatedItem = Item.Builder.anItem()
                .withSeq(1L)
                .withName(updateRequest.getName())
                .withPrice(updateRequest.getPrice())
                .withDescription(updateRequest.getDescription())
                .withStockQuantity(updateRequest.getStockQuantity())
                .build();

        // when
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        this.mockMvc.perform(patch(baseUrl + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(updatedItem.getName())))
                .andExpect(jsonPath("$.data.price", is(updatedItem.getPrice().intValue())))
                .andExpect(jsonPath("$.data.description", is(updatedItem.getDescription())))
                .andExpect(jsonPath("$.data.stockQuantity", is(updatedItem.getStockQuantity())));
    }

    @Test
    @DisplayName("아이템 삭제")
    public void deleteItem() throws Exception {
        // given
        Item item = Item.Builder.anItem()
                .withSeq(1L)
                .withName("item1")
                .withPrice(BigDecimal.valueOf(1000))
                .withDescription("item1 description")
                .build();

        // when
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        doAnswer(invocation -> { when(itemRepository.findById(1L))
                                .thenReturn(Optional.empty()); return null; })
                .when(itemRepository).delete(item);

        this.mockMvc.perform(delete(baseUrl + "/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
