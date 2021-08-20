package org.academy.springbootessentials.repository;

import lombok.extern.log4j.Log4j2;
import org.academy.springbootessentials.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Log4j2
@DisplayName("Tests for Book Repository")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    @DisplayName("Save persists book when Successful")
    void save_PersistBook_WhenSuccessful(){

        Book bookToBeSave = createBook();

        Book bookSaved = this.bookRepository.save(bookToBeSave);

        Assertions.assertThat(bookSaved).isNotNull();

        Assertions.assertThat(bookSaved.getId()).isNotNull();

        Assertions.assertThat(bookSaved.getTitle()).isEqualTo(bookToBeSave.getTitle());
    }

    @Test
    @DisplayName("Save update book when Successful")
    void save_UpdateBook_WhenSuccessful(){
        Book bookToBeSave = createBook();

        Book bookSaved = this.bookRepository.save(bookToBeSave);

        bookSaved.setTitle("The Spiderwick Chronicles");

        Book bookUpdated = this.bookRepository.save(bookSaved);

        Assertions.assertThat(bookUpdated).isNotNull();

        Assertions.assertThat(bookUpdated.getId()).isNotNull();

        Assertions.assertThat(bookUpdated.getTitle()).isEqualTo(bookSaved.getTitle());
    }

    @Test
    @DisplayName("Delete removes book when Successful")
    void delete_RemovesBook_WhenSuccessful(){
        Book bookToBeSaved = createBook();

        Book bookSaved = this.bookRepository.save(bookToBeSaved);

        this.bookRepository.delete(bookSaved);

        Optional<Book> bookOptional = this.bookRepository.findById(bookSaved.getId());

        Assertions.assertThat(bookOptional).isEmpty();

    }

    @Test
    @DisplayName("Find By Title returns list of book when Successful")
    void findByTitle_ReturnsListOfBook_WhenSuccessful(){
        Book bookToBeSaved = createBook();

        Book bookSaved = this.bookRepository.save(bookToBeSaved);

        String title = bookSaved.getTitle();

        List<Book> books = this.bookRepository.findByTitle(title);

        Assertions.assertThat(books)
                .isNotEmpty()
                .contains(bookSaved);


    }

    @Test
    @DisplayName("Find By Title returns empty list when no book is found")
    void findByTitle_ReturnsEmptyList_WhenBookIsNotFound(){
        List<Book> books = this.bookRepository.findByTitle("The Clown");

        Assertions.assertThat(books).isEmpty();
    }


    @Test
    @DisplayName("Save throw ConstraintViolationException when title is empty")
    void save_ThrowsConstraintViolationException_WhenTitleIsEmpty(){
        Book book = new Book();
//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.bookRepository.save(book))
                .withMessageContaining("The book title cannot be empty");
    }


    private Book createBook(){
        return Book.builder()
                .title("The Chronicles of Narnia")
                .build();
    }
}