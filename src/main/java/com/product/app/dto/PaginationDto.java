package com.product.app.dto;

import java.util.List;
import com.product.app.model.Product;
import lombok.*;

@Data
public class PaginationDto {
  private Integer currentPage;
  private Integer perPage;
  private Integer totalCount;
  private List<Product> data;
  private Integer totalPages;

  public Integer getTotalPages() {
    return (int) Math.ceil((double) totalCount / (double) perPage);
  }
}
