package org.academy.springbootessentials.service;

import org.academy.springbootessentials.controller.BookController;
import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.exception.BadRequestException;
import org.academy.springbootessentials.repository.BookRepository;
import org.academy.springbootessentials.requests.BookPostRequestBody;
import org.academy.springbootessentials.requests.BookPutRequestBody;
import org.academy.springbootessentials.util.BookCreator;
import org.academy.springbootessentials.util.BookPostRequestBodyCreator;
import org.academy.springbootessentials.util.BookPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(bookPage);

        BDDMockito.when(bookRepositoryMock.findAll())
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(BookCreator.createValidBook());


        BDDMockito.doNothing().when(bookRepositoryMock).delete(ArgumentMatchers.any(Book.class));
    }
    @Test
    @DisplayName("List all returns list of books inside page object when successful")
    void listAll_ReturnsListOfBooksInsidePageObject_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        Page<Book> bookPage = bookService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(bookPage).isNotNull();

        Assertions.assertThat(bookPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(bookPage.toList().get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("list All Non Pageable returns list of book when successful")
    void listAllNonPageable_ReturnsListOfBook_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookService.listAllNonPageable();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }
    @Test
    @DisplayName("find By Id Or Throw Bad Request Exception returns book when successful")
    void findByIdOrThrowBadRequestException_ReturnsBook_WhenSuccessful(){
        Long expectedId = BookCreator.createValidBook().getId();

        Book book = bookService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when book in not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenBookIssNotFound(){

        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> bookService.findByIdOrThrowBadRequestException(1));

    }

    @Test
    @DisplayName("findBytitle returns a list of book when successful")
    void findByTitle_ReturnsListOfBook_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookService.findByTitle("book");

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByTitle returns an empty list of book when book is not found")
    void findByTitle_ReturnsEmptyListOfBook_WhenBookIsNotFound(){
        BDDMockito.when(bookRepositoryMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Book> books = bookService.findByTitle("book");

        Assertions.assertThat(books)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns book when successful")
    void save_ReturnsBook_WhenSuccessful(){

        Book book = bookService.save(BookPostRequestBodyCreator.createBookPostRequestBody());

        Assertions.assertThat(book).isNotNull().isEqualTo(BookCreator.createValidBook());

    }

    @Test
    @DisplayName("replace updates book when successful")
    void replace_UpdatesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookService.replace(BookPutRequestBodyCreator.createBookPutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_RemovesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookService.delete(1))
                .doesNotThrowAnyException();

    }

}