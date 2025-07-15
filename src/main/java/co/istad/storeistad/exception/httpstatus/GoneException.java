package co.istad.storeistad.exception.httpstatus;

public class GoneException extends RuntimeException {

    private final String message;
    private final Object data;

    public GoneException(String message, Object data) {
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
