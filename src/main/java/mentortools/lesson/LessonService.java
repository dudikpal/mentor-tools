package mentortools.lesson;

import lombok.AllArgsConstructor;
import mentortools.student.CreateStudentCommand;
import mentortools.student.Student;
import mentortools.student.StudentDTO;
import mentortools.student.UpdateStudentCommand;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonService {

    private LessonRepository lessonRepository;

    private ModelMapper modelMapper;

    public List<LessonDTO> getLessons(Optional<String> prefix) {
        return lessonRepository.findAll().stream()
                .filter(lesson -> prefix.isEmpty() || lesson.getTitle().toLowerCase().startsWith(prefix.get().trim().toLowerCase()))
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                .toList();
    }

    public LessonDTO createLesson(CreateLessonCommand command) {

        Lesson lesson = new Lesson(command.getTitle(), command.getUrl());

        return modelMapper.map(lessonRepository.save(lesson), LessonDTO.class);
    }

    public LessonDTO findLessonById(long id) {
        Lesson lesson = getLessonById(id);
        return modelMapper.map(lesson, LessonDTO.class);
    }

    private Lesson getLessonById(long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find student with id: " + id));
    }

    @Transactional
    public LessonDTO updateLesson(long id, UpdateLessonCommand command) {
        Lesson lesson = getLessonById(id);

        lesson.setTitle(command.getTitle());
        lesson.setUrl(command.getUrl());

        return modelMapper.map(lesson, LessonDTO.class);
    }

    public void deleteLesson(long id) {
        lessonRepository.deleteById(id);
    }


}
