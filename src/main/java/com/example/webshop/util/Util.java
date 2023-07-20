package com.example.webshop.util;

import com.example.webshop.models.dto.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;


public class Util {
    public static Page<Product> getPage(Pageable page, List<Product> list)
    {
        int start = (int) page.getOffset();
        int end = Math.min((start + page.getPageSize()), list.size());
        Page<Product> productPage = new PageImpl<>(list.subList(start, end), page, list.size());
        return productPage;

    }


}
