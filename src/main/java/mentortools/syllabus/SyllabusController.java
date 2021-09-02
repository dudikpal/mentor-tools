package mentortools.syllabus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mentortools.lesson.CreateLessonCommand;
import mentortools.lesson.LessonDTO;
import mentortools.lesson.LessonService;
import mentortools.lesson.UpdateLessonCommand;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/syllabuses")
public class SyllabusController {

    private SyllabusService syllabusService;


    @GetMapping
    public List<SyllabusDTO> getSyllabuses(@RequestParam Optional<String> prefix) {
        return syllabusService.getSyllabuses(prefix);
    }


    @PostMapping
    public SyllabusDTO createSyllabus(@Valid @RequestBody CreateSyllabusCommand command) {
        return syllabusService.createSyllabus(command);
    }


    @GetMapping("/{id}")
    public SyllabusDTO findSyllabusById(@PathVariable long id) {
        return syllabusService.findSyllabusById(id);
    }


    @PutMapping("/{id}")
    public SyllabusDTO updateSyllabus(@PathVariable long id, @RequestBody UpdateSyllabusCommand command) {
        return syllabusService.updateSyllabus(id, command);
    }


    @DeleteMapping("/{id}")
    public void deleteSyllabus(@PathVariable long id) {
        syllabusService.deleteSyllabus(id);
    }
}
