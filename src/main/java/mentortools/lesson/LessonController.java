package mentortools.lesson;

import lombok.AllArgsConstructor;
import mentortools.student.CreateStudentCommand;
import mentortools.student.StudentDTO;
import mentortools.student.StudentService;
import mentortools.student.UpdateStudentCommand;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {

    private LessonService lessonService;


    @GetMapping
    public List<LessonDTO> getLessons(@RequestParam Optional<String> prefix) {
        return lessonService.getLessons(prefix);
    }


    @PostMapping
    public LessonDTO createLesson(@Valid @RequestBody CreateLessonCommand command) {
        return lessonService.createLesson(command);
    }


    @GetMapping("/{id}")
    public LessonDTO findLessonById(@PathVariable long id) {
        return lessonService.findLessonById(id);
    }


    @PutMapping("/{id}")
    public LessonDTO updateLesson(@PathVariable long id, @RequestBody UpdateLessonCommand command) {
        return lessonService.updateLesson(id, command);
    }


    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable long id) {
        lessonService.deleteLesson(id);
    }

}
