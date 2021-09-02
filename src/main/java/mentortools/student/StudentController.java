package mentortools.student;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    StudentService studentService;


    @GetMapping
    public List<StudentDTO> getStudents(@RequestParam Optional<String> prefix) {
        return studentService.getStudents(prefix);
    }


    @PostMapping
    public StudentDTO createStudent(@Valid @RequestBody CreateStudentCommand command) {
        return studentService.createStudent(command);
    }


    @GetMapping("/{id}")
    public StudentDTO findStudentById(@PathVariable long id) {
        return studentService.findStudentById(id);
    }


    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable long id, @RequestBody UpdateStudentCommand command) {
        return studentService.updateStudent(id, command);
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }
}
