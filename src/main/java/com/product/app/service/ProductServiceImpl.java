package com.product.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.product.app.dto.PaginationDto;
import com.product.app.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

  private int count = 0;

  @Override
  public void addProduct(List<Product> productList, Map<Integer, Product> dataMap) {
    for (Product product : productList) {
      product.setId(count + 1);
      dataMap.put(count, product);
      count++;
    }
  }

  @Override
  public Integer getTotalPrdoucts(String searchBy, Map<Integer, Product> dataMap) {
    List<Product> totalProducts = new ArrayList<>();

    for (int index = 0; index < dataMap.size(); index++) {
      if (dataMap.containsKey(index) && !searchBy.equals("")) {
        if (dataMap.get(index).getTitle().contains(searchBy)
            || dataMap.get(index).getDescription().contains(searchBy)) {
          totalProducts.add(dataMap.get(index));
        }
      } else if (dataMap.containsKey(index)) {
        totalProducts.add(dataMap.get(index));
      }
    }
    return totalProducts.size();
  }

  @Override
  public PaginationDto findBy(
      PaginationDto paginationDto,
      String sortBy,
      String searchBy,
      Integer totalProducts,
      Map<Integer, Product> dataMap) {
    List<Product> list = new ArrayList<>();

    int startFrom = (paginationDto.getCurrentPage() - 1) * paginationDto.getPerPage();
    int endTo = paginationDto.getCurrentPage() * paginationDto.getPerPage();

    for (int index = startFrom; index < endTo; index++) {
      if (dataMap.containsKey(index) && !searchBy.equals("")) {
        if (dataMap.get(index).getTitle().contains(searchBy)
            || dataMap.get(index).getDescription().contains(searchBy)) {
          list.add(dataMap.get(index));
        }
      } else if (dataMap.containsKey(index) && searchBy.equals("")) {
        list.add(dataMap.get(index));
      }
    }

    switch (sortBy) {
      case "price":
        Collections.sort(list, new ComparatorByPrice());
        break;
      case "brand":
        Collections.sort(list, new ComparatorByBrand());
        break;
      case "color":
        Collections.sort(list, new ComparatorByColor());
        break;
      default:
        Collections.sort(list, new ComparatorById());
        break;
    }

    paginationDto.setTotalCount(totalProducts);
    paginationDto.setData(list);

    return paginationDto;
  }
}

class ComparatorByPrice implements Comparator<Product> {

  @Override
  public int compare(Product s1, Product s2) {
    return s1.getPrice().compareTo(s2.getPrice());
  }
}

class ComparatorByBrand implements Comparator<Product> {

  @Override
  public int compare(Product s1, Product s2) {
    return s1.getBrand().compareTo(s2.getBrand());
  }
}

class ComparatorByColor implements Comparator<Product> {

  @Override
  public int compare(Product s1, Product s2) {
    return s1.getColor().compareTo(s2.getColor());
  }
}

class ComparatorById implements Comparator<Product> {

  @Override
  public int compare(Product s1, Product s2) {
    return s1.getId().compareTo(s2.getId());
  }
}
