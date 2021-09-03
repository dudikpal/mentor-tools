package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.StudentToRegistrationDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredStudentDTO {

    private long id;

    private StudentToRegistrationDTO student;

    private RegistrationStatus registrationStatus;
}
