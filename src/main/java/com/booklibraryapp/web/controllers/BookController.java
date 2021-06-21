package com.booklibraryapp.web.controllers;


import com.booklibraryapp.data.model.Book;
import com.booklibraryapp.service.BookService;
import com.booklibraryapp.web.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("")
public class BookController {


    @Autowired
    BookService bookService;

    @GetMapping("")
    public String index(Model model){
        return "index";
    }

    @GetMapping("book/{id}")
    public String findBookById(@PathVariable("id") String id, Model model){
        try{
            Book book = bookService.findBookById(id);
            model.addAttribute("book",book);
            return "foundBooks";
        }catch (BookNotFoundException ex){
            return "redirect:/";
        }
    }

    @GetMapping("book/all")
    public String search(@RequestParam(name = "bookName") String name, Model model){
        List<Book> books = bookService.search(name);
        model.addAttribute("booklist",books);
        return "books";
    }

}
