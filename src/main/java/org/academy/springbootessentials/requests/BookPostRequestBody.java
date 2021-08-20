package org.academy.springbootessentials.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class BookPostRequestBody {
    @NotEmpty(message = "The book's title")
    @NotNull(message = "The book's title cannot be null")
    private String title;
}
