package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;
import mentortools.trainingclass.TrainingClass;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private TrainingClass trainingClass;

    @OneToOne
    private Student student;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    public Registration(TrainingClass trainingClass, Student student, RegistrationStatus status) {
        this.trainingClass = trainingClass;
        this.student = student;
        this.status = status;
    }
}
