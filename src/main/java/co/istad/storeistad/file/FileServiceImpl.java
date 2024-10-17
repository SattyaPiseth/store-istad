package co.istad.storeistad.file;


import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.UserEntity;
import co.istad.storeistad.file.response.FileRS;
import co.istad.storeistad.mapper.UserEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Sattya
 * create at 2/29/2024 10:21 AM
 */
@Service
@Slf4j
public class FileServiceImpl extends BaseService implements FileService {
    @Value("${file.server-path}")
    private String serverPath;
    @Value("${file.base-uri}")
    private String fileBaseUri;
    @Value("${file.download-uri}")
    private String fileDownloadUri;
    @Override
    public Resource downloadByName(String name) {
        Path path = Paths.get(serverPath+name);
        if (Files.exists(path)){
            return UrlResource.from(path.toUri());
        }
        throw new RuntimeException(MessageConstant.FILE.FILE_NOT_FOUND);
    }
    @Transactional
    @Override
    public void deleteAll() {
        Path path = Paths.get(serverPath);
        try {
            Stream<Path> pathStream = Files.list(path);
            List<Path> pathList = pathStream.toList();
            for (Path p : pathList){
                Files.delete(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Transactional
    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(serverPath+name);
        try {
            if (Files.deleteIfExists(path)){
                return;
            }
            throw new IOException(MessageConstant.FILE.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StructureRS findAll() {
        Path path = Paths.get(serverPath);
        List<FileRS> fileRSList = new ArrayList<>();

        try {
            Stream<Path> pathStream = Files.list(path);
            List<Path> pathList = pathStream.toList();

            for (Path p : pathList){
                Resource resource = UrlResource.from(p.toUri());
                fileRSList.add(FileRS.builder()
                                .name(resource.getFilename())
                                .uri(fileBaseUri+resource.getFilename())
                                .downloadUri(fileDownloadUri+resource.getFilename())
                                .size(resource.contentLength())
                                .extension(getExtension(Objects.requireNonNull(resource.getFilename())))
                                .createdAt(Files.getLastModifiedTime(p).toInstant())

                        .build());
            }
            if (fileRSList.isEmpty()){
                throw new IOException(MessageConstant.FILE.FILE_NOT_FOUND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response(fileRSList);
    }
    private String getExtension(String fileName){
        // Get file extension
        int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex+1);
    }
    @Override
    public FileRS findByName(String name) throws IOException {
        Path path = Paths.get(serverPath+name);
//        String pathWithOutExtension = path.getFileName().toString().replaceFirst("[.][^.]+$", "");
//        log.info("Path without extension: {}", pathWithOutExtension);

        if (Files.exists(path)){
            Resource resource = UrlResource.from(path.toUri());
            return FileRS.builder()
                    .name(resource.getFilename())
                    .uri(fileBaseUri+resource.getFilename())
                    .downloadUri(fileDownloadUri+resource.getFilename())
                    .size(resource.contentLength())
                    .extension(getExtension(Objects.requireNonNull(resource.getFilename())))
                    .build();
        }
        throw new IOException(MessageConstant.FILE.FILE_NOT_FOUND);
    }

    @Transactional
    @Override
    public FileRS uploadSingle(MultipartFile file) {
        if (file.isEmpty()){
            throw new RuntimeException(MessageConstant.FILE.FILE_EMPTY);
        }
        return this.save(file);
    }

    private FileRS save(MultipartFile file){
        String extension = this.getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String name = UUID.randomUUID().toString()+"."+extension;
        String uri = fileBaseUri+name;
        Long size = file.getSize();
        // Create file path (absolute path)
        Path path = Paths.get(serverPath+name);

        // Copy path
        try {
            Files.copy(file.getInputStream(),path);
            return FileRS.builder()
                    .name(name)
                    .uri(uri)
                    .size(size)
                    .downloadUri(fileDownloadUri+name)
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
}
