package netologyru.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netologyru.exceptions.InputDataException;
import netologyru.repository.AuthenticationRepository;
import netologyru.repository.StorageFileRepository;
import netologyru.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import netologyru.dto.request.EditFileNameRequest;
import netologyru.dto.response.FileResponse;
import netologyru.exceptions.UnauthorizedException;
import netologyru.models.StorageFile;
import netologyru.models.User;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StorageFileService {

    private StorageFileRepository storageFileRepository;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;

    public boolean uploadFile(User user, String filename, MultipartFile file) {
        if (user == null) {
            log.error("Upload file: Unauthorized");
            throw new UnauthorizedException("Upload file: Unauthorized");
        }

        try {
            storageFileRepository.save(new StorageFile(filename, LocalDateTime.now(), file.getSize(), file.getBytes(), user));
            log.info("Success upload file. User {}", user.getUsername());
            return true;
        } catch (IOException e) {
            log.error("Upload file: Input data exception");
            throw new InputDataException("Upload file: Input data exception");
        }
    }

    @Transactional
    public void deleteFile(User user, String filename) {
        if (user == null) {
            log.error("Delete file: Unauthorized");
            throw new UnauthorizedException("Delete file: Unauthorized");
        }

        storageFileRepository.deleteByUserAndFilename(user, filename);

        final StorageFile tryingToGetDeletedFile = storageFileRepository.findByUserAndFilename(user, filename);
        if (tryingToGetDeletedFile != null) {
            log.error("Delete file: Input data exception");
            throw new InputDataException("Delete file: Input data exception");
        }
        log.info("Success delete file. User {}", user.getUsername());
    }

    public byte[] downloadFile(User user, String filename) {
        if (user == null) {
            log.error("Download file: Unauthorized");
            throw new UnauthorizedException("Download file: Unauthorized");
        }

        final StorageFile file = storageFileRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            log.error("Download file: Input data exception");
            throw new InputDataException("Download file: Input data exception");
        }
        log.info("Success download file. User {}", user.getUsername());
        return file.getFileContent();
    }

    @Transactional
    public void editFileName(User user, String filename, EditFileNameRequest editFileNameRequest) {
        if (user == null) {
            log.error("Edit file name: Unauthorized");
            throw new UnauthorizedException("Edit file name: Unauthorized");
        }

        storageFileRepository.editFileNameByUser(user, filename, editFileNameRequest.getFilename());

        final StorageFile fileWithOldName = storageFileRepository.findByUserAndFilename(user, filename);
        if (fileWithOldName != null) {
            log.error("Edit file name: Input data exception");
            throw new InputDataException("Edit file name: Input data exception");
        }
        log.info("Success edit file name. User {}", user.getUsername());
    }

    public List<StorageFile> getAllFiles(User user, Integer limit) {
        if (user == null) {
            log.error("Get all files: Unauthorized");
            throw new UnauthorizedException("Get all files: Unauthorized");
        }
        log.info("Success get all files. User {}", user.getUsername());
        return storageFileRepository.findAllByUser(user);
    }
}