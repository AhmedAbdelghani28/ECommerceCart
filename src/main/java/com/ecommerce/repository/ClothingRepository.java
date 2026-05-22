package com.ecommerce.repository;

import com.ecommerce.entity.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    List<Clothing> findByBrandIgnoreCase(String brand);
    List<Clothing> findBySizeIgnoreCase(String size);
}
