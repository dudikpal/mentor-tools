package mentortools.student;

import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    StudentRepository studentRepository;

    ModelMapper modelMapper;

    public List<StudentDTO> getStudents(Optional<String> prefix) {
        return studentRepository.findAll().stream()
                .filter(student -> prefix.isEmpty() || student.getName().toLowerCase().startsWith(prefix.get().trim().toLowerCase()))
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .toList();
    }

    public StudentDTO createStudent(CreateStudentCommand command) {

        Student student = new Student(command.getName(), command.getEmail(), command.getGitHubName());

        return modelMapper.map(studentRepository.save(student), StudentDTO.class);
    }

    public StudentDTO findStudentById(long id) {
        Student student = getStudentById(id);
        return modelMapper.map(student, StudentDTO.class);
    }

    private Student getStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find student with id: " + id));
    }

    @Transactional
    public StudentDTO updateStudent(long id, UpdateStudentCommand command) {
        Student student = getStudentById(id);

        student.setName(command.getName());
        student.setEmail(command.getEmail());
        student.setGitHubName(command.getGitHubName());
        student.setDescription(command.getDescription());

        return modelMapper.map(student, StudentDTO.class);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
}
