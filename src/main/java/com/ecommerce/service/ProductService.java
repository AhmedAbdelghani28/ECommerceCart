package com.ecommerce.service;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.factory.BookFactory;
import com.ecommerce.factory.ClothingFactory;
import com.ecommerce.factory.ShoesFactory;
import com.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final BookRepository bookRepository;
    private final ClothingRepository clothingRepository;
    private final ShoesRepository shoesRepository;
    private final BookFactory bookFactory;
    private final ClothingFactory clothingFactory;
    private final ShoesFactory shoesFactory;

    public ProductService(ProductRepository productRepository, BookRepository bookRepository,
                          ClothingRepository clothingRepository, ShoesRepository shoesRepository,
                          BookFactory bookFactory, ClothingFactory clothingFactory, ShoesFactory shoesFactory) {
        this.productRepository = productRepository;
        this.bookRepository = bookRepository;
        this.clothingRepository = clothingRepository;
        this.shoesRepository = shoesRepository;
        this.bookFactory = bookFactory;
        this.clothingFactory = clothingFactory;
        this.shoesFactory = shoesFactory;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Clothing> getAllClothing() {
        return clothingRepository.findAll();
    }

    public List<Shoes> getAllShoes() {
        return shoesRepository.findAll();
    }

    public Book createBook(BookRequest req) {
        return bookRepository.save(bookFactory.create(req));
    }

    public Clothing createClothing(ClothingRequest req) {
        return clothingRepository.save(clothingFactory.create(req));
    }

    public Shoes createShoes(ShoesRequest req) {
        return shoesRepository.save(shoesFactory.create(req));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
