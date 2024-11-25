package netologyru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "files")
public class StorageFile {

    @Id
    @Column(nullable = false, unique = true)
    private String filename;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Long size;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @ManyToOne
    private User user;

    public StorageFile(String filename, LocalDateTime date, Long size2, byte[] fileContent, User user) {
        this.filename = filename;
        this.date = date;
        this.size = size2;
        this.fileContent = fileContent;
        this.user = user;
    }
}