package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegistrationCommand {

    private long trainingClassId;

    private long studentId;

    private RegistrationStatus registrationStatus;
}
