package com.product.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.product.app.dto.PaginationDto;
import com.product.app.model.Product;

@Service
public interface ProductService {

  void addProduct(List<Product> productList, Map<Integer, Product> dataMap);

  PaginationDto findBy(
      PaginationDto paginationDto,
      String sortBy,
      String searchBy,
      Integer totalProducts,
      Map<Integer, Product> dataMap);

  Integer getTotalPrdoucts(String searchBy, Map<Integer, Product> dataMap);
}
