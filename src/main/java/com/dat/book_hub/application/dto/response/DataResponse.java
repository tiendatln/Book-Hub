package com.dat.book_hub.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor 
@Getter 
@Setter 
@Builder 
public class DataResponse<T> {
    private String message;
    private T body;
}
