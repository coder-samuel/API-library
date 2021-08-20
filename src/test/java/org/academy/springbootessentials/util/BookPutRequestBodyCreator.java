package org.academy.springbootessentials.util;

import org.academy.springbootessentials.requests.BookPutRequestBody;

public class BookPutRequestBodyCreator {
    public static BookPutRequestBody createBookPutRequestBody (){
        return BookPutRequestBody.builder()
                .id(BookCreator.createValidUpdatedBook().getId())
                .title(BookCreator.createValidUpdatedBook().getTitle())
                .build();
    }
}
