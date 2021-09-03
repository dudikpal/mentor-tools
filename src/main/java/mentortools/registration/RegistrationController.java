package mentortools.registration;

import lombok.AllArgsConstructor;
import mentortools.student.RegisterStudentCommand;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RegistrationController {

    private RegistrationService registrationService;


    @GetMapping("/trainingclasses/{id}/registrations")
    public Set<RegisteredStudentDTO> getRegisteredStudents(@PathVariable long id) {
        return registrationService.getRegisteredStudents(id);
    }


    @PostMapping("/trainingclasses/{id}/registrations")
    public void registerStudent(@PathVariable long id, @RequestBody RegisterStudentCommand command) {
        registrationService.registerStudent(id, command);
    }


    @GetMapping("/students/{id}/registrations")
    public Set<RegisteredClassDTO> getRegisteredClasses(@PathVariable long id) {
        return registrationService.getRegisteredClasses(id);
    }
}
