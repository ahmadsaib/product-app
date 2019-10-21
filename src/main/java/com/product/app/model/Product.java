package com.product.app.model;

import lombok.*;

@Getter
@Setter
public class Product {
  private Integer id;
  private String title;
  private String description;
  private String brand;
  private Double price;
  private String color;
}
