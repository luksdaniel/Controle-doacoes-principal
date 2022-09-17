package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptionHandler;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException e){
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceCreateErrorException.class)
    public ResponseEntity handleResourceCreateErrorException(ResourceCreateErrorException e){
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DependencyNotFoundException.class)
    public ResponseEntity handleDependencyNotFoundExceptionn(DependencyNotFoundException e){
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceIntegrityException.class)
    public ResponseEntity handleResourceIntegrityException(ResourceIntegrityException e){
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException e){
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e){
        e.printStackTrace();
        return new ResponseEntity("Ocorreu um erro ao processar a requisição", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e){
        e.printStackTrace();
        return new ResponseEntity("Erro de integridade ao cadastrar o registro", HttpStatus.BAD_REQUEST);
    }

}
