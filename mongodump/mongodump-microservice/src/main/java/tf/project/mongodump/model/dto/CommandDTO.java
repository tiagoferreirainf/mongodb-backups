package tf.project.mongodump.model.dto;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommandDTO implements Serializable {
    private String overridePath;
    private String overrideMongoUri;
    private Boolean useArchive;
    private Boolean useOut;
    private Boolean enableGZIP;
    private String options;
}
