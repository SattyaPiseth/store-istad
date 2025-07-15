package co.istad.storeistad.file;


import co.istad.storeistad.base.BaseController;
import co.istad.storeistad.base.StructureRS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Sattya
 * create at 2/29/2024 11:16 AM
 */
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileController extends BaseController {
    private final FileService fileService;

    @GetMapping
    public StructureRS findAll(){
        return response(fileService.findAll()).getBody();
    }

    @GetMapping("/{name}")
    public StructureRS findByName(@PathVariable String name) throws IOException {
        return response(fileService.findByName(name)).getBody();
    }

    @GetMapping(value = "/download/{name}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> download(@PathVariable String name){
        Resource resource = fileService.downloadByName(name);
        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename="+resource.getFilename())
                .body(resource);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/single",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StructureRS uploadSingle(@RequestPart MultipartFile file){
        log.info("File name: {}", file.getOriginalFilename());
        return response(fileService.uploadSingle(file)).getBody();
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/multiple",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StructureRS uploadMultiple(@RequestPart MultipartFile[] files){
        return response(fileService.uploadMultiple(List.of(files))).getBody();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name){
        fileService.deleteByName(name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAll(){
        fileService.deleteAll();
    }
}
