package com.belajar.springbootthymeleaf.util;

import com.belajar.springbootthymeleaf.model.Response;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> Response<T> build(T data, Map<String, List<String>> errors, Map<String, Object> metadata, HttpStatus httpStatus) {
        return Response.<T>builder()
                .code(httpStatus.value())
                .status(httpStatus.name())
                .data(data)
                .errors(errors)
                .metadata(metadata)
                .build();
    }

}
