package com.sparta.week04_shop.controller;

import com.sparta.week04_shop.domain.Product;
import com.sparta.week04_shop.domain.ProductMypriceRequestDto;
import com.sparta.week04_shop.domain.ProductRepository;
import com.sparta.week04_shop.domain.ProductRequestDto;
import com.sparta.week04_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다. 1week 참고
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductRestController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    // 등록된 전체 관심상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    // 관심 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        productRepository.save(product);
        return product;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id,@RequestBody ProductMypriceRequestDto requestDto){
        return productService.update(id, requestDto);
    }


}
