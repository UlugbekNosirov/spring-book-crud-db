package uz.pdp.services.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dto.file.ResourceDto;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Slf4j
@Service
public class FileStorageService {

    private final String FILE_UPLOAD_PATH = "/upload/b3/test/";
    private final Path rootLocation;

    public FileStorageService() {
        this.rootLocation = Paths.get(FILE_UPLOAD_PATH);
    }

    @PostConstruct
    private void init() {
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
    }

    public ResourceDto store(MultipartFile file) throws IOException {
        ResourceDto.ResourceDtoBuilder builder = ResourceDto.builder();

        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String contentType = file.getContentType();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String generatedName = System.currentTimeMillis() + "." + extension;
        Files.copy(file.getInputStream(), Paths.get(FILE_UPLOAD_PATH, generatedName), StandardCopyOption.REPLACE_EXISTING);
        String path = "/uploads/" + generatedName;

        return ResourceDto.builder().originalName(originalFilename)
                .size(size)
                .contentType(contentType)
                .generatedName(generatedName)
                .path(path)
                .build();

    }
    public String storeImg(MultipartFile img) throws IOException {
        String originalFilename = img.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String generateName = System.currentTimeMillis()+"."+extension;
        Files.copy(img.getInputStream(), Paths.get(FILE_UPLOAD_PATH, generateName), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + generateName;
    }


    public String storeAsync() {

        return "";
    }


    public Resource getResource(String filename) {
        Resource resource = new FileSystemResource(Paths.get(FILE_UPLOAD_PATH, filename));
        return resource;
    }
}
