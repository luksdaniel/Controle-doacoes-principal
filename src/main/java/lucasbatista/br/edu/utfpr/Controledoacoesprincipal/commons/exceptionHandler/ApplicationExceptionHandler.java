package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.entity.ErrorResponse;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceCreateErrorException.class)
    public ResponseEntity<ErrorResponse> handleResourceCreateErrorException(ResourceCreateErrorException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DependencyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDependencyNotFoundExceptionn(DependencyNotFoundException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceIntegrityException.class)
    public ResponseEntity<ErrorResponse> handleResourceIntegrityException(ResourceIntegrityException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<ErrorResponse> handleUnrecognizedPropertyException(UnrecognizedPropertyException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add("Ocorreu um erro ao processar a requisição");
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();
        mensagem.add("Erro de integridade ao cadastrar o registro");
        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e){
        e.printStackTrace();
        List<String> mensagem = new ArrayList<>();

        e.getConstraintViolations().forEach(constraintViolation -> {
            //errors.put(String.valueOf(constraintViolation.getPropertyPath()), constraintViolation.getMessage());
            mensagem.add(constraintViolation.getMessage());
        });

        return new ResponseEntity<>(new ErrorResponse(mensagem), HttpStatus.BAD_REQUEST);
    }

}
