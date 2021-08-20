package org.academy.springbootessentials.util;

import org.academy.springbootessentials.requests.BookPostRequestBody;

public class BookPostRequestBodyCreator {
    public static BookPostRequestBody createBookPostRequestBody (){
    return BookPostRequestBody.builder()
            .title(BookCreator.createBookToBeSaved().getTitle())
            .build();
    }
}
