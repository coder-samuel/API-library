package org.academy.springbootessentials.service;

import lombok.RequiredArgsConstructor;
import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.repository.BookRepository;
import org.academy.springbootessentials.requests.BookPostRequestBody;
import org.academy.springbootessentials.requests.BookPutRequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


    public List<Book> listAll()
    {

        return bookRepository.findAll();
    }

    public Book findByIdOrThrowBadRequestException(long id)
    {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found"));
    }



    public Book save(BookPostRequestBody bookPostRequestBody) {
        return bookRepository.save(Book.builder().title(bookPostRequestBody.getTitle()).build());
    }

    public void delete(long id) {
        bookRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(BookPutRequestBody bookPutRequestBody) {
        Book savedBook = findByIdOrThrowBadRequestException(bookPutRequestBody.getId());
        Book book = Book.builder()
                .id(savedBook.getId())
                .title(bookPutRequestBody.getTitle())
                .build();

        bookRepository.save(book);
    }
}

/*

ThreadLocalRandom
É utilizado para gerar valores aleatórios invocando métodos de instância disponíveis da classe.
Para gerar valor aleátorio sem nenhum limite:
ThreadLocalRandom.current().nextInt());
Para gerar valor aleátorio limitado:
ThreadLocalRandom.current().nextInt(0, 100);
*/