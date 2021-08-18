package org.academy.springbootessentials.requests;

import lombok.Data;

@Data
public class BookPutRequestBody {
    private Long id;
    private String title;
}
