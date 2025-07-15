package co.istad.storeistad.exception.httpstatus;

import lombok.Getter;
public class UnauthorizedException extends RuntimeException {

    private final String message;
    @Getter
    private Object data;

    public UnauthorizedException(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public UnauthorizedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
