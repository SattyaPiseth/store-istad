package co.istad.storeistad.exception.httpstatus;

public class ProxyException extends RuntimeException {

    private final String message;
    private final Object data;

    public ProxyException(String message, Object data) {
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
