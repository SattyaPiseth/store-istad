package co.istad.storeistad.file;


import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.file.response.FileRS;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Sattya
 * create at 2/12/2024 12:28 PM
 */
public interface FileService {
    /**
     * This method is used to download file from server by name
     * @param name of file
     * @return Resource
     */
    Resource downloadByName(String name);
    /**
     * This method is used to delete all files in server
     */
    void deleteAll();
    /**
     * This method is used to delete file from server by name
     * @param name of file
     */
    void deleteByName(String name);
    /**
     * This method is used to retrieve resource meta-data file from server
     * @return List<FileRS>
     */
    StructureRS findAll();
    /**
     * This method is used to retrieve resource(meta-data) files from server
     * @param name of file
     * @return FileRS
     */
    FileRS findByName(String name) throws IOException;
    /**
     * This method is used to upload a single file to server
     * @param file is the request file from client to upload
     * @return FileRS
     */
    FileRS uploadSingle(MultipartFile file);
    /**
     * This method is used to upload a multiple files to server at one time
     * @param files is the request file from client to uploads
     * @return List<FileRS>
     */
    List<FileRS> uploadMultiple(List<MultipartFile> files);
}
