package co.istad.storeistad.exception.httpstatus;

public class TemporaryException extends RuntimeException {

    private final String message;
    private final Object data;

    public TemporaryException(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

}
