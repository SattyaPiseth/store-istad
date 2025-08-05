package co.istad.storeistad.base;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class StructureRS<T> {
    private int status;
    private String message;
    private String messageKey;
    private T data;
    private PagingRS paging;

    public StructureRS() {}

    public StructureRS(T data) {
        this.data = data;
    }

    public StructureRS(T data, PagingRS paging) {
        this.data = data;
        this.paging = paging;
    }

    public StructureRS(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.messageKey = generateMessageKey(message);
    }

    public StructureRS(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.messageKey = generateMessageKey(message);
        this.data = data;
    }
    private String generateMessageKey(String message) {
        return message == null ? "" : message.replaceAll("\\s+", "_").toLowerCase();
    }

}
