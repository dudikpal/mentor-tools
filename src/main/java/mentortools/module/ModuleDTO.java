package mentortools.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {

    private long id;

    private String title;

    private String url;
}
