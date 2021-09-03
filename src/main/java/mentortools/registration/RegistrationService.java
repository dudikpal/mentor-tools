package mentortools.registration;

import lombok.AllArgsConstructor;
import mentortools.student.*;
import mentortools.trainingclass.TrainingClass;
import mentortools.trainingclass.TrainingClassService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegistrationService {

    private RegistrationRepository registrationRepository;

    private ModelMapper modelMapper;

    private TrainingClassService trainingClassService;

    private StudentService studentService;


    public Set<RegisteredStudentDTO> getRegisteredStudents(long id) {

        return registrationRepository.findAll().stream()
                .filter(reg -> reg.getTrainingClass().getId() == id)
                .map(reg -> modelMapper.map(reg, RegisteredStudentDTO.class))
                .collect(Collectors.toSet());
    }


    public void registerStudent(long id, RegisterStudentCommand command) {
        TrainingClass tc = trainingClassService.getTrainingClassById(id);
        Student student = studentService.getStudentById(command.getStudentId());
        Registration registration = new Registration(tc, student, RegistrationStatus.ACTIVE);
        registrationRepository.save(registration);
    }

    public Set<RegisteredClassDTO> getRegisteredClasses(long id) {
        return registrationRepository.findAll().stream()
                .filter(reg -> reg.getStudent().getId() == id)
                .map(reg -> reg.getTrainingClass())
                .map(trainingClass -> modelMapper.map(trainingClass, RegisteredClassDTO.class))
                .collect(Collectors.toSet());
    }
}
