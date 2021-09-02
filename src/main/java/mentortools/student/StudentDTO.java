package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private long id;

    private String name;

    private String email;

    private String gitHubName;

    private String description;
}
