package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class EntityValidateExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e){
        List<String> mensagem = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach((error) ->{
            mensagem.add(error.getDefaultMessage());
        //    String fieldName = ((FieldError) error).getField();
        //    String errorMessage = error.getDefaultMessage();
        //    errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(new ErrorResponse(mensagem),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnprosseasableMsgException(HttpMessageNotReadableException e){
        List<String> mensagem = new ArrayList<>();
        mensagem.add("Entidade fora dos padrões - "+e.getMessage());

        return new ResponseEntity<>(new ErrorResponse(mensagem),HttpStatus.UNPROCESSABLE_ENTITY) ;
    }

}
