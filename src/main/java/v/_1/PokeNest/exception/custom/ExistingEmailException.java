package v._1.PokeNest.exception.custom;

public class ExistingEmailException extends RuntimeException {
    public ExistingEmailException(String message) {
        super(message);
    }
}
