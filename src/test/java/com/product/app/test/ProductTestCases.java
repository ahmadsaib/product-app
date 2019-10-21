package com.product.app.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.product.app.controller.ProductController;
import com.product.app.dto.ApiResponseDto;
import com.product.app.dto.PaginationDto;
import com.product.app.model.Product;
import com.product.app.service.ProductService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ProductTestCases {

  @InjectMocks ProductController productController;

  @Mock ProductService productService;

  @Test
  public void testAddProduct() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    List<Product> listOfProduct = new ArrayList<>();
    Product product = new Product();
    product.setId(1);
    product.setTitle("product1");
    product.setDescription("testing product");
    product.setBrand("TEST");
    product.setPrice(5.0);
    product.setColor("red");
    listOfProduct.add(product);

    ApiResponseDto responseDto = productController.addProduct(listOfProduct);

    assertTrue(responseDto.getStatusCode() == 200);
  }

  @Test
  public void testGetProductWithSerachKeyWord() {

    PaginationDto paginationDto = new PaginationDto();

    Product product1 = new Product();
    product1.setId(1);
    product1.setTitle("product1");
    product1.setDescription("testing product 1");
    product1.setBrand("TEST 1");
    product1.setPrice(5.0);
    product1.setColor("red");

    Product product2 = new Product();
    product2.setId(1);
    product2.setTitle("product2");
    product2.setDescription("testing product 2");
    product2.setBrand("TEST 2");
    product2.setPrice(8.0);
    product2.setColor("blue");

    List<Product> productList = Arrays.asList(product1, product2);

    paginationDto.setData(productList);

    when(productService.findBy(
            any(PaginationDto.class),
            any(String.class),
            any(String.class),
            any(Integer.class),
            any(HashMap.class)))
        .thenReturn(paginationDto);

    // when
    ApiResponseDto result =
        productController.getProducts(
            "{ \"totalPages\": 0, \"totalCount\": 0, \"currentPage\": 1, \"perPage\": 50, \"data\": [] }",
            "",
            "");

    // then
    assertTrue(((PaginationDto) result.getData()).getData().size() == 2);
  }

  @Test
  public void testGetProductWithSerachByKeywordAndSorting() {

    PaginationDto paginationDto = new PaginationDto();

    Product product1 = new Product();
    product1.setId(1);
    product1.setTitle("product1");
    product1.setDescription("testing product 1");
    product1.setBrand("TEST 1");
    product1.setPrice(5.0);
    product1.setColor("red");

    Product product2 = new Product();
    product2.setId(1);
    product2.setTitle("product2");
    product2.setDescription("testing product 2");
    product2.setBrand("TEST 2");
    product2.setPrice(8.0);
    product2.setColor("blue");

    List<Product> productList = Arrays.asList(product1, product2);

    paginationDto.setData(productList);

    when(productService.findBy(
            any(PaginationDto.class),
            any(String.class),
            any(String.class),
            any(Integer.class),
            any(HashMap.class)))
        .thenReturn(paginationDto);
    // when
    ApiResponseDto result =
        productController.getProducts(
            "{ \"totalPages\": 0, \"totalCount\": 0, \"currentPage\": 1, \"perPage\": 50, \"data\": [] }",
            "title",
            "");

    // then
    assertTrue(((PaginationDto) result.getData()).getData().size() == 2);
  }
}
