package mentortools.syllabus;

import lombok.AllArgsConstructor;
import mentortools.lesson.CreateLessonCommand;
import mentortools.lesson.Lesson;
import mentortools.lesson.LessonDTO;
import mentortools.lesson.UpdateLessonCommand;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SyllabusService {

    private SyllabusRepository syllabusRepository;

    private ModelMapper modelMapper;


    public List<SyllabusDTO> getSyllabuses(Optional<String> prefix) {
        return syllabusRepository.findAll().stream()
                .filter(syllabus -> prefix.isEmpty() || syllabus.getName().toLowerCase().startsWith(prefix.get().trim().toLowerCase()))
                .map(syllabus -> modelMapper.map(syllabus, SyllabusDTO.class))
                .toList();
    }

    public SyllabusDTO createSyllabus(CreateSyllabusCommand command) {

        Syllabus syllabus = new Syllabus(command.getName());

        return modelMapper.map(syllabusRepository.save(syllabus), SyllabusDTO.class);
    }

    public SyllabusDTO findSyllabusById(long id) {
        Syllabus syllabus = getSyllabusById(id);
        return modelMapper.map(syllabus, SyllabusDTO.class);
    }

    private Syllabus getSyllabusById(long id) {
        return syllabusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find student with id: " + id));
    }

    @Transactional
    public SyllabusDTO updateSyllabus(long id, UpdateSyllabusCommand command) {
        Syllabus syllabus = getSyllabusById(id);

        syllabus.setName(command.getName());

        return modelMapper.map(syllabus, SyllabusDTO.class);
    }

    public void deleteSyllabus(long id) {
        syllabusRepository.deleteById(id);
    }

}
