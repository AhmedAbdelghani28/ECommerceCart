package com.ecommerce.factory;

import com.ecommerce.dto.ShoesRequest;
import com.ecommerce.entity.Shoes;
import org.springframework.stereotype.Component;

@Component
public class ShoesFactory implements ProductFactory<Shoes, ShoesRequest> {

    @Override
    public Shoes create(ShoesRequest req) {
        return new Shoes(req.getName(), req.getPrice(), req.getDescription(),
                req.getSize(), req.getColor(), req.getMaterial(),
                req.getBrand(), req.getSportType(), req.getGender());
    }
}
