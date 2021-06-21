package com.booklibraryapp.service;

import com.booklibraryapp.data.model.*;
import com.booklibraryapp.web.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Book findBookById(String id) throws BookNotFoundException {
        String URI = "https://www.googleapis.com/books/v1/volumes/" + id;
        ResponseEntity<SearchResults> apiResponseResponseEntity =
                restTemplate.getForEntity(URI, SearchResults.class);
        SearchResults searchResults = apiResponseResponseEntity.getBody();
        if (searchResults == null) {
            throw new BookNotFoundException("ID not found in book list, check for a valid book");
        }
        String bookId = searchResults.getId();
        VolumeInfo volumeInfo = searchResults.getVolumeInfo();
        String title = volumeInfo.getTitle();
        String description = volumeInfo.getDescription();
        ImageLinks imageLinks = volumeInfo.getImageLinks();
        String smallThumbnails = imageLinks.getSmallThumbnails();
        String thumbnail = imageLinks.getThumbnail();
        String previewLinks = volumeInfo.getPreviewLink();
        String publisher = volumeInfo.getPublisher();
        List<String> authors = volumeInfo.getAuthors();
        Book book = new Book();
        book.setId(bookId);
        book.setAuthor(authors);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setDescription(description);
        book.setPreviewLink(previewLinks);
        book.setSmallImage(smallThumbnails);
        book.setImage(thumbnail);
        return book;
    }


    @Override
    public List<Book> search(String searchSentence) {
        searchSentence = searchSentence.replaceAll(" ", "");
        final String URI = "https://www.googleapis.com/books/v1/volumes?q=" + searchSentence;
        ResponseEntity<ApiResponse> apiResponseResponseEntity = restTemplate.getForEntity(URI, ApiResponse.class);
        ApiResponse apiResponse = apiResponseResponseEntity.getBody();
        System.out.println(apiResponse);
        assert apiResponse != null;
        return apiResponse.getItems().stream().map(
                searchResults -> {
                    Book book = new Book();
                    String id = searchResults.getId();
                    VolumeInfo volumeInfo = searchResults.getVolumeInfo();
                    String title = volumeInfo.getTitle();
                    String publisher = volumeInfo.getPublisher();
                    List<String> author = volumeInfo.getAuthors();
                    String previewLink = volumeInfo.getPreviewLink();
                    String description = volumeInfo.getDescription();
                    ImageLinks imageLinks = volumeInfo.getImageLinks();
                    String smallImage = imageLinks.getSmallThumbnails();
                    String image = imageLinks.getThumbnail();
                    book.setId(id);
                    book.setTitle(title);
                    book.setPublisher(publisher);
                    book.setAuthor(author);
                    book.setPreviewLink(previewLink);
                    book.setDescription(description);
                    book.setSmallImage(smallImage);
                    book.setImage(image);
                    return book;
                }
        ).collect(Collectors.toList());

    }
}
