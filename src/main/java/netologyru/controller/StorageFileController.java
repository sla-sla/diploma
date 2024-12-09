package netologyru.controller;

import lombok.AllArgsConstructor;
import netologyru.models.StorageFile;
import netologyru.models.User;
import netologyru.service.AuthenticationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import netologyru.dto.request.EditFileNameRequest;
import netologyru.dto.response.FileResponse;
import netologyru.service.StorageFileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class StorageFileController {

    private StorageFileService cloudStorageService;

    private AuthenticationService authenticationService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename, MultipartFile file) {
        User authuser = authenticationService.getUserByAuthToken(authToken);
        cloudStorageService.uploadFile(authuser, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        User authuser = authenticationService.getUserByAuthToken(authToken);
        cloudStorageService.deleteFile(authuser, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        User authuser = authenticationService.getUserByAuthToken(authToken);
        byte[] file = cloudStorageService.downloadFile(authuser, filename);
        return ResponseEntity.ok().body(new ByteArrayResource(file));
    }

    @PutMapping(value = "/file")
    public ResponseEntity<?> editFileName(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename, @RequestBody EditFileNameRequest editFileNameRequest) {
        User authuser = authenticationService.getUserByAuthToken(authToken);
        cloudStorageService.editFileName(authuser, filename, editFileNameRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<FileResponse> getAllFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") Integer limit) {
        User authuser = authenticationService.getUserByAuthToken(authToken);
        List<StorageFile> files = cloudStorageService.getAllFiles(authuser, limit);
        return files.stream()
                .map(o -> new FileResponse(o.getFilename(), o.getSize()))
                .collect(Collectors.toList());
    }
}