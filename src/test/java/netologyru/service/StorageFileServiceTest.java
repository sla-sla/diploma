package netologyru.service;

import netologyru.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import netologyru.exceptions.InputDataException;
import netologyru.exceptions.UnauthorizedException;
import netologyru.repository.AuthenticationRepository;
import netologyru.repository.StorageFileRepository;
import netologyru.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static netologyru.TestConstants.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StorageFileServiceTest {

    @InjectMocks
    private StorageFileService storageFileService;

    @Mock
    private StorageFileRepository storageFileRepository;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(authenticationRepository.getUsernameByToken(BEARER_TOKEN_SPLIT)).thenReturn(USERNAME_1);
        Mockito.when(userRepository.findByUsername(USERNAME_1)).thenReturn(USER_1);
    }

    @Test
    void uploadFile() {
        assertTrue(storageFileService.uploadFile(USER_1, FILENAME_1, MULTIPART_FILE));
    }

    @Test
    void uploadFileUnauthorized() {
        assertThrows(UnauthorizedException.class, () -> storageFileService.uploadFile(USER_1, FILENAME_1, MULTIPART_FILE));
    }

    @Test
    void deleteFile() {
        storageFileService.deleteFile(USER_1, FILENAME_1);
        Mockito.verify(storageFileRepository, Mockito.times(1)).deleteByUserAndFilename(USER_1, FILENAME_1);
    }

    @Test
    void deleteFileUnauthorized() {
        assertThrows(UnauthorizedException.class, () -> storageFileService.deleteFile(USER_1, FILENAME_1));
    }

    @Test
    void deleteFileInputDataException() {
        Mockito.when(storageFileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(InputDataException.class, () -> storageFileService.deleteFile(USER_1, FILENAME_1));
    }

    @Test
    void downloadFile() {
        Mockito.when(storageFileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertEquals(FILE_CONTENT_1, storageFileService.downloadFile(USER_1, FILENAME_1));
    }

    @Test
    void downloadFileUnauthorized() {
        Mockito.when(storageFileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(UnauthorizedException.class, () -> storageFileService.downloadFile(USER_1, FILENAME_1));
    }

    @Test
    void downloadFileInputDataException() {
        Mockito.when(storageFileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(InputDataException.class, () -> storageFileService.downloadFile(USER_1, FILENAME_2));
    }

    @Test
    void editFileName() {
        storageFileService.editFileName(USER_1, FILENAME_1, EDIT_FILE_NAME_RQ);
        Mockito.verify(storageFileRepository, Mockito.times(1)).editFileNameByUser(USER_1, FILENAME_1, NEW_FILENAME);
    }

    @Test
    void editFileNameUnauthorized() {
        assertThrows(UnauthorizedException.class, () -> storageFileService.editFileName(null, FILENAME_1, EDIT_FILE_NAME_RQ));
    }

    @Test
    void editFileNameInputDataException() {
        Mockito.when(storageFileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(InputDataException.class, () -> storageFileService.deleteFile(USER_1, FILENAME_1));
    }

    @Test
    void getAllFiles() {
        Mockito.when(storageFileRepository.findAllByUser(USER_1)).thenReturn(STORAGE_FILE_LIST);
        assertEquals(FILE_RS_LIST, storageFileService.getAllFiles(USER_1, LIMIT));
    }

    @Test
    void getAllFilesUnauthorized() {
        Mockito.when(storageFileRepository.findAllByUser(USER_1)).thenReturn(STORAGE_FILE_LIST);
        assertThrows(UnauthorizedException.class, () -> storageFileService.getAllFiles(null, LIMIT));
    }
}