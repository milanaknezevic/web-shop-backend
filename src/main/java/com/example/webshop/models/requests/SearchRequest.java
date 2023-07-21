package com.example.webshop.models.requests;

import com.example.webshop.models.dto.ProductAttribute;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {

    private String categoryName;
    private String location;
    private Boolean productStatus;
    private Integer priceFrom;
    private Integer priceTo;
    private List<ProductAttribute> productAttributes;
}