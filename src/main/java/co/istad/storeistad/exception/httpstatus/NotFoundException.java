package co.istad.storeistad.exception.httpstatus;

import lombok.Getter;
public class NotFoundException extends RuntimeException {

    private final String message;
    @Getter
    private Object data;

    public NotFoundException(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
