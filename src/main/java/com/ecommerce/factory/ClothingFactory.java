package com.ecommerce.factory;

import com.ecommerce.dto.ClothingRequest;
import com.ecommerce.entity.Clothing;
import org.springframework.stereotype.Component;

@Component
public class ClothingFactory implements ProductFactory<Clothing, ClothingRequest> {

    @Override
    public Clothing create(ClothingRequest req) {
        return new Clothing(req.getName(), req.getPrice(), req.getDescription(),
                req.getColor(), req.getBrand(), req.getMaterial(),
                req.getGender(), req.getSize());
    }
}
