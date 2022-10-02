package com.sparta.week04_shop.controller;

import com.sparta.week04_shop.NaverShopSearch;
import com.sparta.week04_shop.domain.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    @GetMapping("/api/search") // http://localhost:8080/api/search?query="아이폰"
    public List<ItemDto> getItems(@RequestParam String query){
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(resultString);
    }
}
