package com.example.webshop.models.requests;

import com.example.webshop.models.dto.ProductAttribute;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private String naslov;
    private String imeKategorije;
    private String lokacija;
    private Boolean stanjeProizvoda;
    private Integer cijenaOd;
    private Integer cijenaDo;
    private List<ProductAttribute> proizvodAtributi;
}