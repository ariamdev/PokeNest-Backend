package v._1.PokeNest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import v._1.PokeNest.exception.custom.ExistingEmailException;
import v._1.PokeNest.exception.custom.ExistingUsernameException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistingUsernameException.class)
    public ResponseEntity<String> handleExistingUsernameException(ExistingUsernameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<String> handleExistingEmailException(ExistingEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}
