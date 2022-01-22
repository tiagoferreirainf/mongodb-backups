package tf.project.mongodump.model.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BackupsDTO implements Serializable {
    private List<Backup> backupList;
    private String backupPath;
}
