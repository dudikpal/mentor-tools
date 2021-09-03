package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.registration.RegistrationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentToRegistrationDTO {

    private long id;

    private String name;
}
