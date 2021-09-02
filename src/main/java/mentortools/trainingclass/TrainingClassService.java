package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import mentortools.syllabus.Syllabus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrainingClassService {

    private TrainingClassRepository trainingClassRepository;

    private ModelMapper modelMapper;


    public List<TrainingClassDTO> getTrainingClasses(Optional<String> prefix) {
        return trainingClassRepository.findAll().stream()
                .filter(tc -> prefix.isEmpty() || tc.getName().toLowerCase().startsWith(prefix.get().toLowerCase().trim()))
                .map(tc -> modelMapper.map(tc, TrainingClassDTO.class))
                .toList();
    }

    public TrainingClassDTO createTrainingClass(CreateTrainingClassCommand command) {
        TrainingClass tc = new TrainingClass(command.getName(),
                new Interval(command.getInterval().getStartingDate(), command.getInterval().getEndingDate()));
        return modelMapper.map(trainingClassRepository.save(tc), TrainingClassDTO.class);
    }

    public TrainingClassDTO findTrainingClassById(long id) {
        return modelMapper.map(getTrainingClassById(id), TrainingClassDTO.class);
    }

    private TrainingClass getTrainingClassById(long id) {
        return trainingClassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find student with id: " + id));
    }

    public TrainingClassDTO updateTrainingClass(long id, UpdateTrainingClassCommand command) {
        TrainingClass tc = getTrainingClassById(id);
        tc.setName(command.getName());
        tc.setInterval(new Interval(command.getInterval().getStartingDate(), command.getInterval().getEndingDate()));

        return modelMapper.map(tc, TrainingClassDTO.class);
    }

    public void deleteTrainingClass(long id) {
        trainingClassRepository.deleteById(id);
    }
}
