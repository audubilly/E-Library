package com.booklibraryapp.service;

import com.booklibraryapp.data.model.Book;
import com.booklibraryapp.web.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book findBookById(String id) throws BookNotFoundException;
    List<Book> search(String searchSentence);
    

}
