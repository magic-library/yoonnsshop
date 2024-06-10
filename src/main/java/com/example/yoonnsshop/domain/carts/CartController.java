package com.example.yoonnsshop.domain.carts;


import com.example.yoonnsshop.config.ApiController;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@ApiController
@RequestMapping("carts")
public class CartController {
    @PostMapping("/{itemId}")
    public void addItemToCart(@PathVariable Long itemId, @RequestParam int quantity) {
        HttpSession session = getSession();
        Map<Long, Integer> cart = getCartFromSession(session);
        cart.put(itemId, quantity);
    }

    @GetMapping
    public Map<Long, Integer> getCart() {
        HttpSession session = getSession();
        return getCartFromSession(session);
    }

    @PutMapping("/{itemId}")
    public void updateItemInCart(@PathVariable Long itemId, @RequestParam int quantity) {
        HttpSession session = getSession();
        Map<Long, Integer> cart = getCartFromSession(session);
        if (cart.containsKey(itemId)) {
            cart.put(itemId, quantity);
        }
    }

    @DeleteMapping("/{itemId}")
    public void removeItemFromCart(@PathVariable Long itemId) {
        HttpSession session = getSession();
        Map<Long, Integer> cart = getCartFromSession(session);
        cart.remove(itemId);
    }

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    private Map<Long, Integer> getCartFromSession(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
