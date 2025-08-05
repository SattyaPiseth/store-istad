package co.istad.storeistad.base;

import co.istad.storeistad.constant.MessageConstant;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StructureRS<T> {

    @Builder.Default
    private int status = HttpStatus.OK.value();

    @Builder.Default
    private String message = MessageConstant.SUCCESSFULLY;

    @Builder.Default
    private String messageKey = MessageConstant.SUCCESSFULLY.toLowerCase();

    private T data;

    private PagingRS paging;

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
