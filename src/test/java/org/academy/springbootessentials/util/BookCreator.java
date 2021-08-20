package org.academy.springbootessentials.util;

import org.academy.springbootessentials.domain.Book;

public class BookCreator {

    public static Book createBookToBeSaved(){
        return Book.builder()
                .title("The Chronicles of Narnia")
                .build();
    }

    public static Book createValidBook(){
        return Book.builder()
                .title("The Chronicles of Narnia")
                .id(1L)
                .build();
    }

    public static Book createValidUpdatedBook(){
        return Book.builder()
                .title("Eternal Sunshine of the Spotless Mind")
                .id(1L)
                .build();
    }
}
