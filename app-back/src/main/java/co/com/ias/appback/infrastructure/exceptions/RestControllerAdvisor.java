package co.com.ias.appback.infrastructure.exceptions;

import co.com.ias.appback.infrastructure.exceptions.dto.ResponseModel;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler(value = NullPointerException.class)
    private ResponseEntity<ResponseModel> handleException(NullPointerException e){
        ResponseModel responseModel = new ResponseModel(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    private ResponseEntity<ResponseModel> handleException(IllegalArgumentException e){
        ResponseModel responseModel = new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    private ResponseEntity<ResponseModel> handleException(EntityExistsException e){
        ResponseModel responseModel = new ResponseModel(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    private ResponseEntity<ResponseModel> handleException(IllegalStateException e){
        ResponseModel responseModel = new ResponseModel(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException .class)
    private ResponseEntity<ResponseModel> handleException(HttpMessageNotReadableException e){
        ResponseModel responseModel = new ResponseModel(
                HttpStatus.BAD_REQUEST.value(),
                "Provide a correct Json request");
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ResponseModel>> handleException(MethodArgumentNotValidException e){
        List<ResponseModel> list = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((objectError -> {
            ResponseModel model = new ResponseModel(HttpStatus.BAD_REQUEST.value(), objectError.getDefaultMessage());
            list.add(model);
        }));
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }




}
