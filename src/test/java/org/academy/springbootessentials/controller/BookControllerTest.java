package org.academy.springbootessentials.controller;

import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.service.BookService;
import org.academy.springbootessentials.util.BookCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}

}