package org.academy.springbootessentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.service.BookService;
import org.academy.springbootessentials.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("books")
@Log4j2
@RequiredArgsConstructor
public class BookController {
    private final DateUtil dateUtil;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity <List<Book> >list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok( bookService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity <Book> findById(@PathVariable long id){
        return ResponseEntity.ok( bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book){
     return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT );
    }

    @PutMapping
    public ResponseEntity<Book> replace (@RequestBody Book book) {
        bookService.replace(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}