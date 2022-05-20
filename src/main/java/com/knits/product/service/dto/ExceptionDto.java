package com.knits.product.service.dto;

import com.knits.product.exceptions.AppException;
import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.SystemException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    private int code;
    private String message;

    public ExceptionDto(AppException e){
        setCode(e.getCode());
        setMessage(e.getMessage());
    }

}
