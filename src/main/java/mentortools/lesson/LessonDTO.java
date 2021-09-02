package mentortools.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {

    private long id;

    private String title;

    private String url;
}
