package co.istad.storeistad.exception.httpstatus;

import lombok.Getter;

public class BadRequestException extends RuntimeException {

    private final String message;
    @Getter
    private Object data;

    public BadRequestException(String message, String messageKey, Object data) {
        this.message = message;
        this.data = data;
    }
    public BadRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
