package org.academy.springbootessentials.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPutRequestBody {
    private Long id;
    private String title;
}
