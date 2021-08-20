package org.academy.springbootessentials.controller;

import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.requests.BookPostRequestBody;
import org.academy.springbootessentials.requests.BookPutRequestBody;
import org.academy.springbootessentials.service.BookService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(bookPage);

        BDDMockito.when(bookServiceMock.listAllNonPageable())
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookServiceMock.save(ArgumentMatchers.any(BookPostRequestBody.class)))
                .thenReturn(BookCreator.createValidBook());

        BDDMockito.doNothing().when(bookServiceMock).replace(ArgumentMatchers.any(BookPutRequestBody.class));

        BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyLong());
    }
    @Test
    @DisplayName("List returns list of books inside page object when successful")
    void list_ReturnsListOfBooksInsidePageObject_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        Page<Book> bookPage = bookController.list(null).getBody();

        Assertions.assertThat(bookPage).isNotNull();

        Assertions.assertThat(bookPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(bookPage.toList().get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("listAll returns list of book when successful")
    void listAll_ReturnsListOfBook_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookController.listAll().getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }
    @Test
    @DisplayName("findById returns book when successful")
    void findById_ReturnsBook_WhenSuccessful(){
        Long expectedId = BookCreator.createValidBook().getId();

        Book book = bookController.findById(1).getBody();

        Assertions.assertThat(book).isNotNull();

        Assertions.assertThat(book.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findBytitle returns a list of book when successful")
    void findByTitle_ReturnsListOfBook_WhenSuccessful(){
        String expectedTitle = BookCreator.createValidBook().getTitle();

        List<Book> books = bookController.findByTitle("book").getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(books.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByTitle returns an empty list of book when book is not found")
    void findByTitle_ReturnsEmptyListOfBook_WhenBookIsNotFound(){
        BDDMockito.when(bookServiceMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Book> books = bookController.findByTitle("book").getBody();

        Assertions.assertThat(books)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns book when successful")
    void save_ReturnsBook_WhenSuccessful(){

        Book book = bookController.save(BookPostRequestBodyCreator.createBookPostRequestBody()).getBody();

        Assertions.assertThat(book).isNotNull().isEqualTo(BookCreator.createValidBook());

    }

    @Test
    @DisplayName("replace updates book when successful")
    void replace_UpdatesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookController.replace(BookPutRequestBodyCreator.createBookPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.replace(BookPutRequestBodyCreator.createBookPutRequestBody());
        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes book when successful")
    void delete_RemovesBook_WhenSuccessful(){

        Assertions.assertThatCode(() ->bookController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = bookController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}

