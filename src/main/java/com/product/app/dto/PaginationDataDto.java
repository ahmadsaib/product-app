package com.product.app.dto;

import lombok.*;

@Getter
@Setter
public class PaginationDataDto {
  private Integer totalPages;
  private Integer from;
  private Integer to;
}
