package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;
import mentortools.student.StudentDTO;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDTO {

    private long id;

    private String name;

    private Interval interval;

    private Set<StudentDTO> students;
}
