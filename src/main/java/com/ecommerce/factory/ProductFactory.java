package com.ecommerce.factory;

import com.ecommerce.entity.Product;

public interface ProductFactory<T extends Product, R> {
    T create(R request);
}
