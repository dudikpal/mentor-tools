package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mentortools.student.Student;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainingclasses")
public class TrainingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max = 255)
    private String name;

    @Embedded
    private Interval interval;

    /*@OneToMany
    private Set<Student> students;*/

    public TrainingClass(String name, Interval interval) {
        this.name = name;
        this.interval = interval;
    }


    /*public void addRegisteredStudent(Student student) {
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(student);
    }*/
}

