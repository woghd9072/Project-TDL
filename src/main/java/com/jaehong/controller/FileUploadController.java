package com.jaehong.controller;

import com.jaehong.domain.File;
import com.jaehong.domain.ToDoList;
import com.jaehong.file.StorageFileNotFoundException;
import com.jaehong.file.StorageService;
import com.jaehong.repository.FileRepository;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    FileRepository fileRepository;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("idx") Integer idx) {

        storageService.store(file);

        ToDoList toDoList = toDoListRepository.findByIdx(idx);
        File file1 = new File();
        file1.setName(file.getOriginalFilename());
        file1.setUploadedDate(LocalDateTime.now());
        file1.setUrl(storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(
                FileUploadController.class, "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()).toString());
        file1.setToDoList(toDoList);
        fileRepository.save(file1);
        toDoList.add(file1);

        return "redirect:/tdl/list";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
