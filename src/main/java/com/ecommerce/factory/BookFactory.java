package com.ecommerce.factory;

import com.ecommerce.dto.BookRequest;
import com.ecommerce.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookFactory implements ProductFactory<Book, BookRequest> {

    @Override
    public Book create(BookRequest req) {
        return new Book(req.getName(), req.getPrice(), req.getDescription(),
                req.getAuthor(), req.getIsbn(), req.getPages(),
                req.getGenre(), req.getPublishYear(), req.getLanguage());
    }
}
