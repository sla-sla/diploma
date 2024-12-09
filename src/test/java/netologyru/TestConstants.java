package netologyru;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;
import netologyru.dto.request.AuthenticationRequest;
import netologyru.dto.request.EditFileNameRequest;
import netologyru.dto.response.AuthenticationResponse;
import netologyru.dto.response.FileResponse;
import netologyru.models.StorageFile;
import netologyru.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class TestConstants {

    public static final String TOKEN_1 = "Token1";
    public static final String TOKEN_2 = "Token2";
    public static final String BEARER_TOKEN = "Bearer Token";
    public static final String BEARER_TOKEN_SPLIT = BEARER_TOKEN.split(" ")[1];
    public static final String BEARER_TOKEN_SUBSTRING_7 = BEARER_TOKEN.substring(7);

    public static final String USERNAME_1 = "Username1";
    public static final String PASSWORD_1 = "Password1";
    public static final User USER_1 = new User(USERNAME_1, PASSWORD_1, null);

    public static final String USERNAME_2 = "Username2";
    public static final String PASSWORD_2 = "Password2";
    public static final User USER_2 = new User(USERNAME_2, PASSWORD_2, null);

    public static final String FILENAME_1 = "Filename1";
    public static final Long SIZE_1 = 100L;
    public static final byte[] FILE_CONTENT_1 = FILENAME_1.getBytes();
    public static final StorageFile STORAGE_FILE_1 = new StorageFile(FILENAME_1, LocalDateTime.now(), SIZE_1, FILE_CONTENT_1, USER_1);

    public static final String FILENAME_2 = "Filename2";
    public static final Long SIZE_2 = 101L;
    public static final byte[] FILE_CONTENT_2 = FILENAME_2.getBytes();
    public static final StorageFile STORAGE_FILE_2 = new StorageFile(FILENAME_2, LocalDateTime.now(), SIZE_2, FILE_CONTENT_2, USER_2);

    public static final String FOR_RENAME_FILENAME = "OldFilename";
    public static final Long FOR_RENAME_SIZE = 103L;
    public static final byte[] FOR_RENAME_FILE_CONTENT = FOR_RENAME_FILENAME.getBytes();
    public static final StorageFile FOR_RENAME_STORAGE_FILE = new StorageFile(FOR_RENAME_FILENAME, LocalDateTime.now(), FOR_RENAME_SIZE, FOR_RENAME_FILE_CONTENT, USER_1);

    public static final String NEW_FILENAME = "NewFilename";
    public static final EditFileNameRequest EDIT_FILE_NAME_RQ = new EditFileNameRequest(NEW_FILENAME);

    public static final MultipartFile MULTIPART_FILE = new MockMultipartFile(FILENAME_2, FILE_CONTENT_2);

    public static final List<StorageFile> STORAGE_FILE_LIST = List.of(STORAGE_FILE_1, STORAGE_FILE_2);

    public static final StorageFile FILE_RS_1 = new StorageFile(FILENAME_1, null, SIZE_1, null, null);
    public static final StorageFile FILE_RS_2 = new StorageFile(FILENAME_2, null, SIZE_2, null, null);
    public static final List<StorageFile> FILE_RS_LIST = List.of(FILE_RS_1, FILE_RS_2);
    public static final Integer LIMIT = 100;

    public static final AuthenticationRequest AUTHENTICATION_RQ = new AuthenticationRequest(USERNAME_1, PASSWORD_1);
    public static final AuthenticationResponse AUTHENTICATION_RS = new AuthenticationResponse(TOKEN_1);

    public static final UsernamePasswordAuthenticationToken USERNAME_PASSWORD_AUTHENTICATION_TOKEN = new UsernamePasswordAuthenticationToken(USERNAME_1, PASSWORD_1);
}