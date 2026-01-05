package co.istad.storeistad.file;

import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.file.response.FileRS;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileServiceImpl extends BaseService implements FileService {

    @Value("${file.server-path}")
    private String serverPath;

    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Value("${file.download-uri}")
    private String fileDownloadUri;

    private Path storageDir;

    @PostConstruct
    public void init() throws IOException {
        storageDir = Paths.get(serverPath).toAbsolutePath().normalize();
        Files.createDirectories(storageDir);
        log.info("File storage directory: {}", storageDir);
    }

    private String safeName(String name) {
        // prevents ../../etc/passwd style traversal
        return Paths.get(Objects.requireNonNull(name)).getFileName().toString();
    }

    private Path resolvePath(String name) {
        return storageDir.resolve(safeName(name)).normalize();
    }

    @Override
    public Resource downloadByName(String name) {
        try {
            Path path = resolvePath(name);
            if (Files.exists(path) && Files.isRegularFile(path)) {
                return UrlResource.from(path.toUri());
            }
            throw new RuntimeException(MessageConstant.FILE.FILE_NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        try (Stream<Path> stream = Files.list(storageDir)) {
            stream.filter(Files::isRegularFile)
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        Path path = resolvePath(name);
        try {
            if (!Files.deleteIfExists(path)) {
                throw new IOException(MessageConstant.FILE.FILE_NOT_FOUND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StructureRS findAll() {
        List<FileRS> fileRSList = new ArrayList<>();

        try (Stream<Path> stream = Files.list(storageDir)) {
            List<Path> pathList = stream.filter(Files::isRegularFile).toList();

            for (Path p : pathList) {
                Resource resource = UrlResource.from(p.toUri());
                String filename = Objects.requireNonNull(resource.getFilename());

                fileRSList.add(FileRS.builder()
                        .name(filename)
                        .uri(fileBaseUri + filename)
                        .downloadUri(fileDownloadUri + filename)
                        .size(resource.contentLength())
                        .extension(getExtension(filename))
                        .createdAt(Files.getLastModifiedTime(p).toInstant())
                        .build());
            }

            // ✅ IMPORTANT: do NOT throw when empty; return []
            return response(fileRSList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileRS findByName(String name) throws IOException {
        Path path = resolvePath(name);

        if (Files.exists(path) && Files.isRegularFile(path)) {
            Resource resource = UrlResource.from(path.toUri());
            String filename = Objects.requireNonNull(resource.getFilename());

            return FileRS.builder()
                    .name(filename)
                    .uri(fileBaseUri + filename)
                    .downloadUri(fileDownloadUri + filename)
                    .size(resource.contentLength())
                    .extension(getExtension(filename))
                    .build();
        }
        throw new IOException(MessageConstant.FILE.FILE_NOT_FOUND);
    }

    @Transactional
    @Override
    public FileRS uploadSingle(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException(MessageConstant.FILE.FILE_EMPTY);
        }
        return save(file);
    }

    private FileRS save(MultipartFile file) {
        String original = Objects.requireNonNull(file.getOriginalFilename());
        String extension = getExtension(original);

        String name = UUID.randomUUID() + (extension.isBlank() ? "" : "." + extension);
        Path path = resolvePath(name);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return FileRS.builder()
                    .name(name)
                    .uri(fileBaseUri + name)
                    .size(file.getSize())
                    .downloadUri(fileDownloadUri + name)
                    .extension(extension)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public List<FileRS> uploadMultiple(List<MultipartFile> files) {
        return files.stream().map(this::save).toList();
    }

    private String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        return (i >= 0 && i < fileName.length() - 1) ? fileName.substring(i + 1) : "";
    }
}
