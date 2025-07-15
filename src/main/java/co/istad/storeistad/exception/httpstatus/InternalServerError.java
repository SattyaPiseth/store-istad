package co.istad.storeistad.exception.httpstatus;

import lombok.Getter;
public class InternalServerError extends RuntimeException {

    private final String message;
    @Getter
    private Object data;

    public InternalServerError(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public InternalServerError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
