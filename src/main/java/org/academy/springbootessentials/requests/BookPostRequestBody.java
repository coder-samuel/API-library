package org.academy.springbootessentials.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookPostRequestBody {
    @NotEmpty(message = "The book's title")
    @NotNull(message = "The book's title cannot be null")
    private String title;
}
