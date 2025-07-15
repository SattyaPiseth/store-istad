package co.istad.storeistad.file.response;

import co.istad.storeistad.db.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @author Sattya
 * create at 2/12/2024 12:30 PM
 */
@Data
@Builder
public class FileRS {
    private String name;
    private String uri;
    private Long size;
    private String downloadUri;
    private String extension;
    private Instant createdAt;
    private UserEntity createdBy;
}
