package org.academy.springbootessentials.service;

import lombok.RequiredArgsConstructor;
import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.exception.BadRequestException;
import org.academy.springbootessentials.mapper.BookMapper;
import org.academy.springbootessentials.repository.BookRepository;
import org.academy.springbootessentials.requests.BookPostRequestBody;
import org.academy.springbootessentials.requests.BookPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


    public Page<Book> listAll(Pageable pageable)
    {
        return bookRepository.findAll(pageable);
    }

    public List<Book> listAllNonPageable() {
        return bookRepository.findAll();
    }

    public List<Book> findByTitle(String title)
    {
        return bookRepository.findByTitle(title);
    }

    public Book findByIdOrThrowBadRequestException(long id)
    {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Book not found"));
    }



    public Book save(BookPostRequestBody bookPostRequestBody) {
        return bookRepository.save(BookMapper.INSTANCE.toBook(bookPostRequestBody));
    }

    public void delete(long id) {
        bookRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(BookPutRequestBody bookPutRequestBody) {
        Book savedBook = findByIdOrThrowBadRequestException(bookPutRequestBody.getId());
        Book book = BookMapper.INSTANCE.toBook(bookPutRequestBody);
        book.setId(savedBook.getId());

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