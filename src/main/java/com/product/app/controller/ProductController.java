package com.product.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.app.dto.ApiResponseDto;
import com.product.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.product.app.dto.PaginationDto;
import com.product.app.model.Product;
import com.product.app.service.ProductService;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ProductController {

  private Map<Integer, Product> dataMap = new HashMap<>();

  @Autowired private ProductService productService;

  @RequestMapping(
      value = "/product/add",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public ApiResponseDto addProduct(@RequestBody List<Product> productList) {
    ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
    productService.addProduct(productList, dataMap);
    apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK);
    return apiResponseDtoBuilder.build();
  }

  @RequestMapping(
      value = "/product/get/all",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public ApiResponseDto getProducts(
      @RequestParam(required = true) String details,
      @RequestParam(required = false, defaultValue = "") String sortBy,
      @RequestParam(required = false, defaultValue = "") String searchBy) {
    ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(details);
      PaginationDto paginationDto = mapper.treeToValue(node, PaginationDto.class);
      Integer totalProducts = productService.getTotalPrdoucts(searchBy, dataMap);
      paginationDto =
          productService.findBy(paginationDto, sortBy, searchBy, totalProducts, dataMap);
      apiResponseDtoBuilder
          .withMessage("success")
          .withStatus(HttpStatus.OK)
          .withData(paginationDto);
    } catch (Exception e) {
      log.info(e.getMessage());
      apiResponseDtoBuilder
          .withMessage("fail")
          .withStatus(HttpStatus.BAD_REQUEST)
          .withMessage("Something Went Wrong");
    }
    return apiResponseDtoBuilder.build();
  }
}
