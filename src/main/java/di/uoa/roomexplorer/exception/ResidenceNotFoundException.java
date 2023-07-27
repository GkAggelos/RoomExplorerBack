package di.uoa.roomexplorer.exception;

public class ResidenceNotFoundException extends RuntimeException {
    public ResidenceNotFoundException(String message) {
        super(message);
    }
}
