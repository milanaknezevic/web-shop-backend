package com.example.webshop.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {
    @NotNull
    private String naslov;
    @NotNull
    private String opis;
    @NotNull
    private BigDecimal cijena;
    @NotNull
    private Boolean stanje;
    @NotNull
    private String lokacija;
    @NotNull
    private String kontakt;
    @NotNull
    private Integer kategorijaId;
    @NotNull
    private List<ProductAtributeRequest> proizvodAtributs;
    @NotNull
    private List<ImageRequest> slikas;

}
