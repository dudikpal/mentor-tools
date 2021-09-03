package mentortools.trainingclass;

import mentortools.student.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {

 /*   @Query("select ")
    Set<StudentDTO> getRegisteredStudents(long id);*/
}
