package com.ecommerce.repository;

import com.ecommerce.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {
    List<Shoes> findByBrandIgnoreCase(String brand);
    List<Shoes> findBySize(int size);
}
