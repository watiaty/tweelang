package by.waitaty.learnlanguage.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {

    private String message;
    private String status;
    private String error;

}
