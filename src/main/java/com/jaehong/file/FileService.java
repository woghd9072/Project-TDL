package com.jaehong.file;

import com.jaehong.domain.ToDoList;
import com.jaehong.file.exception.FileDownloadException;
import com.jaehong.file.exception.FileUploadException;
import com.jaehong.file.property.FileProperty;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    private final Path fileLocation;

    @Autowired
    public FileService(FileProperty property) {

        this.fileLocation = Paths.get(property.getLocation());
    }

    public String storeFile(MultipartFile file, Map<String, String> map) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains(".."))
                throw new FileUploadException("부적합 문자가 포함되어 있습니다." + fileName);

            Path targetLocation = this.fileLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            File files = new File();
            ToDoList toDoList = toDoListRepository.findByIdx(Integer.parseInt(map.get("idx")));
            files.setName(file.getOriginalFilename());
            files.setUrl(targetLocation.toString());
            files.setSize(file.getSize());
            files.setUploadedDate(LocalDateTime.now());
            toDoList.addFile(files);

            return fileName;
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file..", e);
        }
    }


    public Resource loadAsResource(String fileName) {

        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new FileDownloadException(fileName + "not found", e);
        }
    }

    public void init() {
        try {
            Files.createDirectory(fileLocation);
        } catch (IOException e) {
            throw new FileUploadException("Could not initialize storage", e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(fileLocation.toFile());
    }
}
