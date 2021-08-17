package org.academy.springbootessentials.service;

import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookService {
    //private final BookRepository bookRepository;
    private static List<Book> books;

    static {
        books = new ArrayList<>(List.of(new Book(1L, "The Chronicles of Narnia"), new Book(2L, "The Spiderwick Chronicles")));
    }

    public List<Book> listAll()
    {
        return books;
    }

    public Book findById(long id)
    {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found"));
    }

    public Book save(Book book) {
        book.setId(ThreadLocalRandom.current().nextLong(1, 1000));
        books.add(book);
        return book;
    }

    public void delete(long id) {
        books.remove(findById(id));
    }

    public void replace(Book book) {
    delete(book.getId());
    books.add(book);
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