package org.academy.springbootessentials.mapper;

import org.academy.springbootessentials.domain.Book;
import org.academy.springbootessentials.requests.BookPostRequestBody;
import org.academy.springbootessentials.requests.BookPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    public abstract Book toBook(BookPostRequestBody bookPostRequestBody);
    public abstract Book toBook(BookPutRequestBody bookPutRequestBody);

}
