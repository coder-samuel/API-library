package org.academy.springbootessentials.repository;

import org.academy.springbootessentials.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> listAll();
}
