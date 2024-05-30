package by.waitaty.learnlanguage.exception;

import by.waitaty.learnlanguage.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler({})
//    public ErrorResponseDto handleNotFoundException(RuntimeException ex) {
//        log.info(ex.getMessage(), ex);
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
//        errorResponseDto.setStatus("404");
//        errorResponseDto.setMessage(ex.getMessage());
//        return errorResponseDto;
//    }

//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    @ExceptionHandler({})
//    public ErrorResponseDto handleTimeException(RuntimeException ex) {
//        log.info(ex.getMessage(), ex);
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
//        errorResponseDto.setStatus("406");
//        errorResponseDto.setMessage(ex.getMessage());
//        return errorResponseDto;
//    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({})
//    public ErrorResponseDto handleEventTypeException(RuntimeException ex) {
//        log.info(ex.getMessage(), ex);
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
//        errorResponseDto.setStatus("400");
//        errorResponseDto.setMessage(ex.getMessage());
//        return errorResponseDto;
//    }

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler({})
//    public ErrorResponseDto handleUserNotEnoughRightException(RuntimeException ex) {
//        log.info(ex.getMessage(), ex);
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
//        errorResponseDto.setStatus("403");
//        errorResponseDto.setMessage(ex.getMessage());
//        return errorResponseDto;
//    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleAuthenticationException(RuntimeException ex) {
        log.info(ex.getMessage(), ex);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("401");
        errorResponseDto.setError(ex.getMessage());
        return errorResponseDto;
    }

//    @ExceptionHandler({})
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponseDto handleConflictException(RuntimeException ex) {
//        log.info(ex.getMessage(), ex);
//        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
//        errorResponseDto.setStatus("409");
//        errorResponseDto.setError(ex.getMessage());
//        return errorResponseDto;
//    }
}
