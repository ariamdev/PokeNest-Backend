package v._1.PokeNest.pets.exception.custom;

public class ExistingUsernameException extends RuntimeException {
    public ExistingUsernameException(String message) {
        super(message);
    }
}
