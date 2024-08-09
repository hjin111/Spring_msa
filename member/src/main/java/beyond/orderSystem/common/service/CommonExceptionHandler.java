package beyond.orderSystem.common.service;

import beyond.orderSystem.common.dto.CommonErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CommonExceptionHandler {

    //  Controller 단에서 발생 하는 모든 EntityNotFoundException catch
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonErrorDto> entityNotFoundHandler(EntityNotFoundException e){
        e.printStackTrace();
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>( commonErrorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonErrorDto> IllegalHandler(IllegalArgumentException e){
        e.printStackTrace();
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorDto> validHandler(MethodArgumentNotValidException e){
        e.printStackTrace();
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonErrorDto> exceptionHandler(Exception e){
        e.printStackTrace();
        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error");
        return new ResponseEntity<>(commonErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
